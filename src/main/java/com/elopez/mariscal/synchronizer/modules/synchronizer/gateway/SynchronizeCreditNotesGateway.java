package com.elopez.mariscal.synchronizer.modules.synchronizer.gateway;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.math.BigDecimal;

import com.elopez.mariscal.synchronizer.modules.synchronizer.service.output.synchronizerOutputBoundary;

import com.elopez.mariscal.synchronizer.modules.auditor.controller.AuditController;
import com.elopez.mariscal.synchronizer.modules.auditor.entity.Document;
import com.elopez.mariscal.synchronizer.modules.auditor.errors.DocumentNotFound;
import com.elopez.mariscal.synchronizer.modules.auditor.gateway.DocumentGateway;
import com.elopez.mariscal.synchronizer.modules.auditor.service.AuditInteractor;

import com.elopez.mariscal.synchronizer.modules.retriever.controller.RetrieveCreditNotesController;
import com.elopez.mariscal.synchronizer.modules.retriever.gateway.RetrieveCreditNotesJpa;
import com.elopez.mariscal.synchronizer.modules.retriever.service.RetrieveCreditNotesInteractor;

import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentToSend;
import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentType;
import com.elopez.mariscal.synchronizer.modules.sender.controller.SendDocumentController;
import com.elopez.mariscal.synchronizer.modules.sender.entity.Currency;
import com.elopez.mariscal.synchronizer.modules.sender.gateway.SendDocumentMariscal;
import com.elopez.mariscal.synchronizer.modules.sender.presenter.SendDocumentPresenter;
import com.elopez.mariscal.synchronizer.modules.sender.service.SendDocumentInteractor;

public class SynchronizeCreditNotesGateway implements synchronizerOutputBoundary {

    @Override
    public void synchronize() throws Exception {
        var retriever = getRetriever();
        var creditNotes = retriever.retrieveCreditNotes();
        synchronizeCreditNotes(creditNotes);
    }

    private RetrieveCreditNotesController getRetriever() {
        var gateway = new RetrieveCreditNotesJpa();
        var interactor = new RetrieveCreditNotesInteractor(gateway);
        var retrieverController = new RetrieveCreditNotesController(interactor);
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

    private void synchronizeCreditNote(Map<String, Object> creditNote, SendDocumentController sender, AuditController auditor)
            throws Exception {
        var documentToAudit = convertToDocumentToAudit(creditNote);
        try {
            auditor.findDocument(documentToAudit);
        } catch (DocumentNotFound e) {
            var documentToSend = convertToDocumentToSend(creditNote);
            sender.sendDocument(documentToSend);
            documentToAudit.hasError = false;
            auditor.saveDocument(documentToAudit);
        }
    }

    private SendDocumentController getSender() {
        var gateway = new SendDocumentMariscal();
        var presenter = new SendDocumentPresenter(gateway);
        var interactor = new SendDocumentInteractor(presenter);
        var controller = new SendDocumentController(interactor);
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

    private DocumentToSend convertToDocumentToSend(Map<String, Object> creditNote) {
        var document = new DocumentToSend();
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
        document.tipo = DocumentType.NCR;
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