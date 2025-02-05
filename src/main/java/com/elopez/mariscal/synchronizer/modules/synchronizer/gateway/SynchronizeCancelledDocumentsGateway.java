package com.elopez.mariscal.synchronizer.modules.synchronizer.gateway;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

import com.elopez.mariscal.synchronizer.modules.synchronizer.service.output.synchronizerOutputBoundary;
import com.elopez.mariscal.synchronizer.modules.auditor.controller.AuditController;
import com.elopez.mariscal.synchronizer.modules.auditor.entity.AuditDocument;
import com.elopez.mariscal.synchronizer.modules.auditor.errors.DocumentNotFound;
import com.elopez.mariscal.synchronizer.modules.auditor.gateway.AuditGateway;
import com.elopez.mariscal.synchronizer.modules.auditor.service.AuditInteractor;
import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentToCancel;
import com.elopez.mariscal.synchronizer.modules.sender.controller.SendCancelDocumentController;
import com.elopez.mariscal.synchronizer.modules.sender.entity.Currency;
import com.elopez.mariscal.synchronizer.modules.sender.gateway.SendCancelDocumentMariscal;
import com.elopez.mariscal.synchronizer.modules.sender.presenter.SendCancelDocumentPresenter;
import com.elopez.mariscal.synchronizer.modules.sender.service.SendCancelDocumentInteractor;
import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.output.RetrieverOutputBoundary;

public abstract class SynchronizeCancelledDocumentsGateway implements synchronizerOutputBoundary {

    protected abstract RetrieverOutputBoundary getRetriever();

    protected abstract AuditDocument mapToAuditDocument(Map<String, Object> document);

    protected abstract DocumentToCancel mapToDocumentToSend(Map<String, Object> document);

    protected abstract String getToken();

    protected abstract String getContractNumber();

    protected abstract int getMaxSentAttempts();

    private final AuditGateway documentGateway;

    public SynchronizeCancelledDocumentsGateway(AuditGateway documentGateway) {
        this.documentGateway = documentGateway;
    }

    @Override
    public void synchronize() throws Exception {
        List<Map<String, Object>> documents = getRetriever().retrieve();
        processDocuments(documents);
    }

    private void processDocuments(List<Map<String, Object>> documents) throws Exception {
        SendCancelDocumentController senderController = initializeSenderController();
        AuditController auditController = initializeAuditController();

        for (Map<String, Object> document : documents) {
            processSingleDocument(document, senderController, auditController);
        }
    }

    private void processSingleDocument(Map<String, Object> document, SendCancelDocumentController senderController,
            AuditController auditController) throws Exception {
        AuditDocument auditDocument = mapToAuditDocument(document);

        switch (shouldRetryDocument(auditDocument, auditController)) {
            case "RETRY":
                synchronizeDocument(document, senderController, auditController, auditDocument, true);
                break;
            case "CANCEL":
                synchronizeDocument(document, senderController, auditController, auditDocument, false);
                break;
            case "IGNORE":
                break;
        }
    }

    private String shouldRetryDocument(AuditDocument auditDocument, AuditController auditController) throws Exception {
        try {
            Map<String, Object> documentInfo = auditController.findDocument(auditDocument);

            Boolean canceledSuccess = (Boolean) documentInfo.get("canceledSuccess");
            Integer canceledAttempts = (Integer) documentInfo.get("canceledAttempts");

            if (canceledSuccess == null || canceledAttempts == null) {
                return "CANCEL";
            }

            if (!canceledSuccess && canceledAttempts < getMaxSentAttempts()) {
                return "RETRY";
            }
            return "IGNORE";
        } catch (DocumentNotFound e) {
            return "IGNORE";
        }

    }

    private void synchronizeDocument(Map<String, Object> document, SendCancelDocumentController senderController,
            AuditController auditController, AuditDocument auditDocument, boolean isRetry) throws Exception {
        try {
            DocumentToCancel documentToSend = mapToDocumentToSend(document);
            senderController.sendCancelDocument(documentToSend);
            auditDocument.hasError = false;
        } catch (Exception e) {
            auditDocument.hasError = true;
        }
        cancelOrUpdateAuditDocument(auditDocument, auditController, isRetry);
    }

    private void cancelOrUpdateAuditDocument(AuditDocument auditDocument, AuditController auditController,
            boolean isRetry)
            throws Exception {
        if (isRetry) {
            auditController.updateDocumentCanceled(auditDocument);
        } else {
            auditController.cancelDocument(auditDocument);
        }
    }

    private SendCancelDocumentController initializeSenderController() {
        SendCancelDocumentMariscal sendGateway = new SendCancelDocumentMariscal(getToken());
        SendCancelDocumentPresenter presenter = new SendCancelDocumentPresenter(sendGateway, getContractNumber());
        SendCancelDocumentInteractor interactor = new SendCancelDocumentInteractor(presenter);
        return new SendCancelDocumentController(interactor);
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