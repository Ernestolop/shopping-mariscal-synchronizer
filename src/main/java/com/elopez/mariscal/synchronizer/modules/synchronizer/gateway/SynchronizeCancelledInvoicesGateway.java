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
import com.elopez.mariscal.synchronizer.modules.retriever.gateway.RetrieveCancelledInvoicesJpa;
import com.elopez.mariscal.synchronizer.modules.retriever.service.RetrieverInteractor;

import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentToCancel;
import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentType;
import com.elopez.mariscal.synchronizer.modules.sender.controller.SendCancelDocumentController;
import com.elopez.mariscal.synchronizer.modules.sender.entity.Currency;
import com.elopez.mariscal.synchronizer.modules.sender.gateway.SendCancelDocumentMariscal;
import com.elopez.mariscal.synchronizer.modules.sender.presenter.SendCancelDocumentPresenter;
import com.elopez.mariscal.synchronizer.modules.sender.service.SendCancelDocumentInteractor;

public class SynchronizeCancelledInvoicesGateway implements synchronizerOutputBoundary {

    Logger logger = LoggerFactory.getLogger(SynchronizeCancelledInvoicesGateway.class);

    @Override
    public void synchronize() throws Exception {
        var retriever = getRetriever();
        var Invoices = retriever.retrieveCancelledInvoices();
        synchronizeInvoices(Invoices);
    }

    private RetrieveController getRetriever() {
        var gateway = new RetrieveCancelledInvoicesJpa();
        var interactor = new RetrieverInteractor();
        interactor.setCancelledInvoicesOutputBoundary(gateway);
        var retrieverController = new RetrieveController();
        retrieverController.setRetrieveCancelledInvoicesInputBoundary(interactor);
        return retrieverController;
    }

    private void synchronizeInvoices(List<Map<String, Object>> newInvoices) throws Exception {
        var sender = getSender();
        var auditor = getAuditor();
        for (int i = 0; i < newInvoices.size(); i++) {
            var invoice = newInvoices.get(i);
            synchronizeInvoice(invoice, sender, auditor);
        }
    }

    private void synchronizeInvoice(Map<String, Object> invoice, SendCancelDocumentController sender,
            AuditController auditor)
            throws Exception {
        var documentToAudit = convertToDocumentToAudit(invoice);
        try {
            var document = auditor.findDocument(documentToAudit);
            if (!(boolean) document.get("canceled")) {
                var documentToSend = convertToDocumentToSend(invoice);
                sender.sendCancelDocument(documentToSend);
                documentToAudit.hasError = false;
                auditor.saveDocument(documentToAudit);
            }

        } catch (DocumentNotFound e) {
            logger.error("No se puede enviar la factura anulada con el id " + invoice.get("id")
                    + " porque no se encontro en auditoria");
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

    private Document convertToDocumentToAudit(Map<String, Object> invoice) {
        var document = new Document();
        document.id = (String) invoice.get("id");
        document.type = com.elopez.mariscal.synchronizer.modules.auditor.entity.DocumentType.FAC;
        return document;
    }

    private DocumentToCancel convertToDocumentToSend(Map<String, Object> invoice) {
        var document = new DocumentToCancel();
        document.comprobante = (String) invoice.get("comprobante");
        document.fecha = (LocalDate) invoice.get("fecha");
        document.moneda = currency((String) invoice.get("moneda"));
        document.cliente = (String) invoice.get("cliente");
        document.ruc = (String) invoice.get("ruc");
        document.tipoCambio = tipoCambio((BigDecimal) invoice.get("tipoCambio"), document.moneda);
        document.gravadas10 = (BigDecimal) invoice.get("gravadas10");
        document.gravadas5 = (BigDecimal) invoice.get("gravadas5");
        document.exentas = (BigDecimal) invoice.get("exentas");
        document.total = (BigDecimal) invoice.get("total");
        document.anulado = (LocalDate) invoice.get("anulado");
        document.tipo = DocumentType.AFACT;
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
