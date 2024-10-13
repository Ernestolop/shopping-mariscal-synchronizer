package com.elopez.mariscal.synchronizer.modules.synchronizer.gateway;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.math.BigDecimal;

import com.elopez.mariscal.synchronizer.modules.synchronizer.service.output.synchronizerOutputBoundary;

import com.elopez.mariscal.synchronizer.modules.auditor.controller.AuditController;
import com.elopez.mariscal.synchronizer.modules.auditor.entity.Document;
import com.elopez.mariscal.synchronizer.modules.auditor.errors.DocumentNotFound;
import com.elopez.mariscal.synchronizer.modules.auditor.gateway.DocumentGateway;
import com.elopez.mariscal.synchronizer.modules.auditor.service.AuditInteractor;

import com.elopez.mariscal.synchronizer.modules.retriever.controller.RetrieveController;
import com.elopez.mariscal.synchronizer.modules.retriever.gateway.RetrieveInvoicesJpa;
import com.elopez.mariscal.synchronizer.modules.retriever.service.RetrieverInteractor;

import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentToSend;
import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentType;
import com.elopez.mariscal.synchronizer.modules.sender.controller.SendDocumentController;
import com.elopez.mariscal.synchronizer.modules.sender.entity.Currency;
import com.elopez.mariscal.synchronizer.modules.sender.gateway.SendDocumentMariscal;
import com.elopez.mariscal.synchronizer.modules.sender.presenter.SendDocumentPresenter;
import com.elopez.mariscal.synchronizer.modules.sender.service.SendDocumentInteractor;

@Component
public class SynchronizeInvoicesGateway implements synchronizerOutputBoundary {

    private RetrieveInvoicesJpa retrieveInvoicesJpa;
    private DocumentGateway documentGateway;

    @Value("${mariscal.contract.number}")
    private String contractNumber;

    public SynchronizeInvoicesGateway(RetrieveInvoicesJpa retrieveInvoicesJpa, DocumentGateway documentGateway) {
        this.retrieveInvoicesJpa = retrieveInvoicesJpa;
        this.documentGateway = documentGateway;
    }

    @Override
    public void synchronize() throws Exception {
        var retriever = getRetriever();
        var Invoices = retriever.retrieveInvoices();
        synchronizeInvoices(Invoices);
    }

    private RetrieveController getRetriever() {
        var interactor = new RetrieverInteractor();
        interactor.setInvoicesOutputBoundary(retrieveInvoicesJpa);
        var retrieverController = new RetrieveController();
        retrieverController.setRetrieveInvoicesInputBoundary(interactor);
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

    private void synchronizeInvoice(Map<String, Object> invoice, SendDocumentController sender, AuditController auditor)
            throws Exception {
        var documentToAudit = convertToDocumentToAudit(invoice);
        try {
            auditor.findDocument(documentToAudit);
        } catch (DocumentNotFound e) {
            var documentToSend = convertToDocumentToSend(invoice);
            sender.sendDocument(documentToSend);
            documentToAudit.hasError = false;
            auditor.saveDocument(documentToAudit);
        }
    }

    private SendDocumentController getSender() {
        var gateway = new SendDocumentMariscal();
        var presenter = new SendDocumentPresenter(gateway, contractNumber);
        var interactor = new SendDocumentInteractor(presenter);
        var controller = new SendDocumentController(interactor);
        return controller;
    }

    private AuditController getAuditor() {
        var interactor = new AuditInteractor(documentGateway);
        var controller = new AuditController(interactor);
        return controller;
    }

    private Document convertToDocumentToAudit(Map<String, Object> invoice) {
        var document = new Document();
        document.id = invoice.get("id").toString();
        document.type = com.elopez.mariscal.synchronizer.modules.auditor.entity.DocumentType.FAC;
        return document;
    }

    private DocumentToSend convertToDocumentToSend(Map<String, Object> invoice) {
        var document = new DocumentToSend();
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
        document.tipo = DocumentType.FACT;
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
