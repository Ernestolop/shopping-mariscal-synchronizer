package com.elopez.mariscal.synchronizer.modules.synchronizer.gateway.cancelled;

import java.util.Map;
import java.time.LocalDate;
import java.math.BigDecimal;

import com.elopez.mariscal.synchronizer.modules.auditor.entity.AuditDocument;
import com.elopez.mariscal.synchronizer.modules.auditor.entity.AuditDocumentType;
import com.elopez.mariscal.synchronizer.modules.auditor.gateway.AuditGateway;
import com.elopez.mariscal.synchronizer.modules.retriever.gateway.RetrieveCancelledCreditNotesJpa;
import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentToCancel;
import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentType;
import com.elopez.mariscal.synchronizer.modules.synchronizer.gateway.SynchronizeCancelledDocumentsGateway;
import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.output.RetrieverOutputBoundary;

public class SynchronizeCancelledCreditNotesGateway extends SynchronizeCancelledDocumentsGateway {

    protected String contractNumber;

    protected int maxSentAttempts;

    private final RetrieveCancelledCreditNotesJpa retrieveCancelledCreditNotesJpa;

    public SynchronizeCancelledCreditNotesGateway(RetrieveCancelledCreditNotesJpa retrieveCancelledCreditNotesJpa,
            AuditGateway documentGateway, String contractNumber, int maxSentAttempts) {
        super(documentGateway);
        this.retrieveCancelledCreditNotesJpa = retrieveCancelledCreditNotesJpa;
        this.contractNumber = contractNumber;
        this.maxSentAttempts = maxSentAttempts;
    }

    @Override
    protected String getContractNumber() {
        return contractNumber;
    }

    @Override
    protected int getMaxSentAttempts() {
        return maxSentAttempts;
    }

    @Override
    protected RetrieverOutputBoundary getRetriever() {
        return retrieveCancelledCreditNotesJpa;
    }

    @Override
    protected AuditDocument mapToAuditDocument(Map<String, Object> document) {
        AuditDocument auditDocument = new AuditDocument();
        auditDocument.id = document.get("id").toString();
        auditDocument.type = AuditDocumentType.NCR;
        return auditDocument;
    }

    @Override
    protected DocumentToCancel mapToDocumentToSend(Map<String, Object> document) {
        DocumentToCancel documentToSend = new DocumentToCancel();
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
        documentToSend.anulado = (LocalDate) document.get("anulado");
        documentToSend.tipo = DocumentType.ANCR;
        return documentToSend;
    }
}