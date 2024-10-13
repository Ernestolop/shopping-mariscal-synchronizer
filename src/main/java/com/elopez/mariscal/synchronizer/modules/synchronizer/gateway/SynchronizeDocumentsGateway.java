package com.elopez.mariscal.synchronizer.modules.synchronizer.gateway;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;

import com.elopez.mariscal.synchronizer.modules.synchronizer.service.output.synchronizerOutputBoundary;
import com.elopez.mariscal.synchronizer.modules.auditor.controller.AuditController;
import com.elopez.mariscal.synchronizer.modules.auditor.entity.AuditDocument;
import com.elopez.mariscal.synchronizer.modules.auditor.errors.DocumentNotFound;
import com.elopez.mariscal.synchronizer.modules.auditor.gateway.AuditGateway;
import com.elopez.mariscal.synchronizer.modules.auditor.service.AuditInteractor;
import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.output.RetrieverOutputBoundary;
import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentToSend;
import com.elopez.mariscal.synchronizer.modules.sender.controller.SendDocumentController;
import com.elopez.mariscal.synchronizer.modules.sender.entity.Currency;
import com.elopez.mariscal.synchronizer.modules.sender.gateway.SendDocumentMariscal;
import com.elopez.mariscal.synchronizer.modules.sender.presenter.SendDocumentPresenter;
import com.elopez.mariscal.synchronizer.modules.sender.service.SendDocumentInteractor;

public abstract class SynchronizeDocumentsGateway implements synchronizerOutputBoundary {

    protected abstract RetrieverOutputBoundary getRetriever();

    protected abstract AuditDocument mapToAuditDocument(Map<String, Object> document);

    protected abstract DocumentToSend mapToDocumentToSend(Map<String, Object> document);

    @Value("${mariscal.contract.number}")
    private String contractNumber;

    @Value("${auditor.sent.attempts}")
    private int maxSentAttempts;

    private final AuditGateway documentGateway;

    public SynchronizeDocumentsGateway(AuditGateway documentGateway) {
        this.documentGateway = documentGateway;
    }

    @Override
    public void synchronize() throws Exception {
        List<Map<String, Object>> documents = getRetriever().retrieve();
        processDocuments(documents);
    }

    private void processDocuments(List<Map<String, Object>> documents) throws Exception {
        SendDocumentController senderController = initializeSenderController();
        AuditController auditController = initializeAuditController();

        for (Map<String, Object> document : documents) {
            processSingleDocument(document, senderController, auditController);
        }
    }

    private void processSingleDocument(Map<String, Object> document, SendDocumentController senderController,
            AuditController auditController) throws Exception {
        AuditDocument auditDocument = mapToAuditDocument(document);

        if (shouldRetryDocument(auditDocument, auditController)) {
            synchronizeDocument(document, senderController, auditController, auditDocument, true);
        } else {
            synchronizeDocument(document, senderController, auditController, auditDocument, false);
        }
    }

    private boolean shouldRetryDocument(AuditDocument auditDocument, AuditController auditController) throws Exception {
        try {
            Map<String, Object> documentInfo = auditController.findDocument(auditDocument);
            boolean hasError = (boolean) documentInfo.get("hasError");
            int sentAttempts = (int) documentInfo.get("sentAttempts");

            return hasError && sentAttempts < maxSentAttempts;
        } catch (DocumentNotFound e) {
            return false;
        }
    }

    private void synchronizeDocument(Map<String, Object> document, SendDocumentController senderController,
            AuditController auditController, AuditDocument auditDocument, boolean isRetry) throws Exception {
        try {
            DocumentToSend documentToSend = mapToDocumentToSend(document);
            senderController.sendDocument(documentToSend);
            auditDocument.hasError = false;
        } catch (Exception e) {
            auditDocument.hasError = true;
        }
        saveOrUpdateAuditDocument(auditDocument, auditController, isRetry);
    }

    private void saveOrUpdateAuditDocument(AuditDocument auditDocument, AuditController auditController,
            boolean isRetry)
            throws Exception {
        if (isRetry) {
            auditController.updateDocument(auditDocument);
        } else {
            auditController.saveDocument(auditDocument);
        }
    }

    private SendDocumentController initializeSenderController() {
        SendDocumentMariscal sendGateway = new SendDocumentMariscal();
        SendDocumentPresenter presenter = new SendDocumentPresenter(sendGateway, contractNumber);
        SendDocumentInteractor interactor = new SendDocumentInteractor(presenter);
        return new SendDocumentController(interactor);
    }

    private AuditController initializeAuditController() {
        AuditInteractor auditInteractor = new AuditInteractor(documentGateway);
        return new AuditController(auditInteractor);
    }

    protected Currency mapToCurrency(String currencyCode) {
        switch (currencyCode) {
            case "USD":
                return Currency.US;
            case "GS":
            default:
                return Currency.GS;
        }
    }

    protected BigDecimal getTipoCambio(BigDecimal tipoCambio, Currency currency) {
        return currency.isLocal() ? BigDecimal.ONE : tipoCambio;
    }
}