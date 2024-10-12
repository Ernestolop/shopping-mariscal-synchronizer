package com.elopez.mariscal.synchronizer.modules.synchronizer.gateway;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.math.BigDecimal;

import com.elopez.mariscal.synchronizer.modules.synchronizer.service.output.synchronizerOutputBoundary;

import com.elopez.mariscal.synchronizer.modules.auditor.controller.AuditController;
import com.elopez.mariscal.synchronizer.modules.auditor.entity.Document;
import com.elopez.mariscal.synchronizer.modules.auditor.errors.DocumentNotFound;
import com.elopez.mariscal.synchronizer.modules.auditor.gateway.DocumentGateway;
import com.elopez.mariscal.synchronizer.modules.auditor.service.AuditInteractor;

import com.elopez.mariscal.synchronizer.modules.retriever.controller.RetrieveController;
import com.elopez.mariscal.synchronizer.modules.retriever.gateway.RetrieveCancelledCreditNotesJpa;
import com.elopez.mariscal.synchronizer.modules.retriever.service.RetrieverInteractor;

import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentToCancel;
import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentType;
import com.elopez.mariscal.synchronizer.modules.sender.controller.SendCancelDocumentController;
import com.elopez.mariscal.synchronizer.modules.sender.entity.Currency;
import com.elopez.mariscal.synchronizer.modules.sender.gateway.SendCancelDocumentMariscal;
import com.elopez.mariscal.synchronizer.modules.sender.presenter.SendCancelDocumentPresenter;
import com.elopez.mariscal.synchronizer.modules.sender.service.SendCancelDocumentInteractor;

public class SynchronizeCancelledCreditNotesGateway implements synchronizerOutputBoundary {

    Logger logger = LoggerFactory.getLogger(SynchronizeCancelledCreditNotesGateway.class);

    @Override
    public void synchronize() throws Exception {
        var retriever = getRetriever();
        var creditNotes = retriever.retrieveCancelledCreditNotes();
        synchronizeCreditNotes(creditNotes);
    }

    private RetrieveController getRetriever() {
        var gateway = new RetrieveCancelledCreditNotesJpa();
        var interactor = new RetrieverInteractor();
        interactor.setCancelledCreditNotesOutputBoundary(gateway);
        var retrieverController = new RetrieveController();
        retrieverController.setRetrieveCancelledCreditNotesInputBoundary(interactor);
        return retrieverController;
    }

    private void synchronizeCreditNotes(List<Map<String, Object>> newCreditNotes) throws Exception {
        var sender = getSender();
        var auditor = getAuditor();
        for (int i = 0; i < newCreditNotes.size(); i++) {
            var creditNote = newCreditNotes.get(i);
            synchronizeCreditNote(creditNote, sender, auditor);
        }
    }

    private void synchronizeCreditNote(Map<String, Object> creditNote, SendCancelDocumentController sender,
            AuditController auditor)
            throws Exception {
        var documentToAudit = convertToDocumentToAudit(creditNote);
        try {
            var document = auditor.findDocument(documentToAudit);
            if (!(boolean) document.get("canceled")) {
                var documentToSend = convertToDocumentToSend(creditNote);
                sender.sendCancelDocument(documentToSend);
                documentToAudit.hasError = false;
                auditor.saveDocument(documentToAudit);
            }

        } catch (DocumentNotFound e) {
            logger.error("No se puede enviar la nota de crédito anulada con el id " + creditNote.get("id")
                    + " porque no se encontró en auditoría");
        }
    }

    private SendCancelDocumentController getSender() {
        var gateway = new SendCancelDocumentMariscal();
        var presenter = new SendCancelDocumentPresenter(gateway);
        var interactor = new SendCancelDocumentInteractor(presenter);
        var controller = new SendCancelDocumentController(interactor);
        return controller;
    }

    private AuditController getAuditor() {
        var gateway = new DocumentGateway();
        var interactor = new AuditInteractor(gateway);
        var controller = new AuditController(interactor);
        return controller;
    }

    private Document convertToDocumentToAudit(Map<String, Object> creditNote) {
        var document = new Document();
        document.id = (String) creditNote.get("id");
        document.type = com.elopez.mariscal.synchronizer.modules.auditor.entity.DocumentType.NCR;
        return document;
    }

    private DocumentToCancel convertToDocumentToSend(Map<String, Object> creditNote) {
        var document = new DocumentToCancel();
        document.comprobante = (String) creditNote.get("comprobante");
        document.fecha = (LocalDate) creditNote.get("fecha");
        document.moneda = currency((String) creditNote.get("moneda"));
        document.cliente = (String) creditNote.get("cliente");
        document.ruc = (String) creditNote.get("ruc");
        document.tipoCambio = tipoCambio((BigDecimal) creditNote.get("tipoCambio"), document.moneda);
        document.gravadas10 = (BigDecimal) creditNote.get("gravadas10");
        document.gravadas5 = (BigDecimal) creditNote.get("gravadas5");
        document.exentas = (BigDecimal) creditNote.get("exentas");
        document.total = (BigDecimal) creditNote.get("total");
        document.anulado = (LocalDate) creditNote.get("anulado");
        document.tipo = DocumentType.ANCR;
        return document;
    }

    private Currency currency(String docCur) {
        switch (docCur) {
            case "USD":
                return Currency.US;
            case "GS":
                return Currency.GS;
            default:
                return Currency.GS;
        }
    }

    private BigDecimal tipoCambio(BigDecimal tipoCambio, Currency moneda) {
        if (moneda.isLocal()) {
            return BigDecimal.ONE;
        } else {
            return tipoCambio;
        }
    }

}