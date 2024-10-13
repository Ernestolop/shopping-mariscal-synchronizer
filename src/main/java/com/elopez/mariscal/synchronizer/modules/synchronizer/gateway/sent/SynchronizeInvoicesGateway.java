package com.elopez.mariscal.synchronizer.modules.synchronizer.gateway.sent;

import java.util.Map;
import java.time.LocalDate;
import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.elopez.mariscal.synchronizer.modules.auditor.entity.AuditDocument;
import com.elopez.mariscal.synchronizer.modules.auditor.entity.AuditDocumentType;
import com.elopez.mariscal.synchronizer.modules.auditor.gateway.AuditGateway;
import com.elopez.mariscal.synchronizer.modules.retriever.gateway.RetrieveInvoicesJpa;
import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentToSend;
import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentType;
import com.elopez.mariscal.synchronizer.modules.synchronizer.gateway.SynchronizeDocumentsGateway;
import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.output.RetrieverOutputBoundary;

@Component
public class SynchronizeInvoicesGateway extends SynchronizeDocumentsGateway {

    private final RetrieveInvoicesJpa retrieveInvoicesJpa;

    public SynchronizeInvoicesGateway(RetrieveInvoicesJpa retrieveInvoicesJpa, AuditGateway documentGateway) {
        super(documentGateway);
        this.retrieveInvoicesJpa = retrieveInvoicesJpa;
    }

    @Override
    protected RetrieverOutputBoundary getRetriever() {
        return retrieveInvoicesJpa;
    }

    @Override
    protected AuditDocument mapToAuditDocument(Map<String, Object> document) {
        AuditDocument auditDocument = new AuditDocument();
        auditDocument.id = document.get("comprobante").toString();
        auditDocument.type = AuditDocumentType.FAC;
        return auditDocument;
    }

    @Override
    protected DocumentToSend mapToDocumentToSend(Map<String, Object> document) {
        DocumentToSend documentToSend = new DocumentToSend();
        documentToSend.comprobante = (String) document.get("comprobante");
        documentToSend.fecha = (LocalDate) document.get("fecha");
        documentToSend.moneda = mapToCurrency((String) document.get("moneda"));
        documentToSend.cliente = (String) document.get("cliente");
        documentToSend.ruc = (String) document.get("ruc");
        documentToSend.tipoCambio = getTipoCambio((BigDecimal) document.get("tipoCambio"), documentToSend.moneda);
        documentToSend.gravadas10 = (BigDecimal) document.get("gravadas10");
        documentToSend.gravadas5 = (BigDecimal) document.get("gravadas5");
        documentToSend.exentas = (BigDecimal) document.get("exentas");
        documentToSend.total = (BigDecimal) document.get("total");
        documentToSend.tipo = DocumentType.FACT;
        return documentToSend;
    }
}