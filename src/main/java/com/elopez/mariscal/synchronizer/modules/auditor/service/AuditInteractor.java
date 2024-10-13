package com.elopez.mariscal.synchronizer.modules.auditor.service;

import java.util.List;
import java.util.Map;

import com.elopez.mariscal.synchronizer.modules.auditor.entity.AuditDocument;
import com.elopez.mariscal.synchronizer.modules.auditor.service.input.AuditInputBoundary;
import com.elopez.mariscal.synchronizer.modules.auditor.service.output.AuditOuputBoundary;

public class AuditInteractor implements AuditInputBoundary {

    private AuditOuputBoundary outputBoundary;

    public AuditInteractor(AuditOuputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void saveDocument(AuditDocument document) throws Exception {
        outputBoundary.saveDocument(document);
    }

    @Override
    public void updateDocument(AuditDocument document) throws Exception {
        outputBoundary.updateDocument(document);
    }    

    @Override
    public void cancelDocument(AuditDocument document) throws Exception {
        outputBoundary.cancelDocument(document);
    }

    @Override
    public void updateDocumentCanceled(AuditDocument document) throws Exception {
        outputBoundary.updateDocumentCanceled(document);
    }

    @Override
    public Map<String, Object> findDocument(AuditDocument document) throws Exception {
        return outputBoundary.findDocument(document);
    }

    @Override
    public List<Map<String, Object>> findSentErrorDocuments() throws Exception {
        return outputBoundary.findSentErrorDocuments();
    }

    @Override
    public List<Map<String, Object>> findCanceledErrorDocuments() throws Exception {
        return outputBoundary.findCanceledErrorDocuments();
    }
    
}
