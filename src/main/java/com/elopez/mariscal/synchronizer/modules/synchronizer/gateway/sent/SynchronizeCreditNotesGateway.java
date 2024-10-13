package com.elopez.mariscal.synchronizer.modules.synchronizer.gateway.sent;

import java.util.Map;
import java.time.LocalDate;
import java.math.BigDecimal;

import com.elopez.mariscal.synchronizer.modules.auditor.entity.AuditDocument;
import com.elopez.mariscal.synchronizer.modules.auditor.entity.AuditDocumentType;
import com.elopez.mariscal.synchronizer.modules.auditor.gateway.AuditGateway;
import com.elopez.mariscal.synchronizer.modules.retriever.gateway.RetrieveCreditNotesJpa;
import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentToSend;
import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentType;
import com.elopez.mariscal.synchronizer.modules.synchronizer.gateway.SynchronizeDocumentsGateway;
import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.output.RetrieverOutputBoundary;

public class SynchronizeCreditNotesGateway extends SynchronizeDocumentsGateway {

    private String token;

    private String contractNumber;

    private int maxSentAttempts;
    
    private final RetrieveCreditNotesJpa retrieveCreditNotesJpa;

    public SynchronizeCreditNotesGateway(RetrieveCreditNotesJpa retrieveCreditNotesJpa, AuditGateway documentGateway, String contractNumber, int maxSentAttempts, String token) {
        super(documentGateway);
        this.retrieveCreditNotesJpa = retrieveCreditNotesJpa;
        this.contractNumber = contractNumber;
        this.maxSentAttempts = maxSentAttempts;
        this.token = token;
    }

    @Override
    protected String getToken() {
        return token;
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
        return retrieveCreditNotesJpa;
    }

    @Override
    protected AuditDocument mapToAuditDocument(Map<String, Object> document) {
        AuditDocument auditDocument = new AuditDocument();
        auditDocument.id = document.get("id").toString();
        auditDocument.type = AuditDocumentType.NCR;
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
        documentToSend.tipo = DocumentType.NCR;
        return documentToSend;
    }
}