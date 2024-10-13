package com.elopez.mariscal.synchronizer.modules.auditor.controller;

import java.util.List;
import java.util.Map;

import com.elopez.mariscal.synchronizer.modules.auditor.entity.AuditDocument;
import com.elopez.mariscal.synchronizer.modules.auditor.service.input.AuditInputBoundary;

public class AuditController {

    private AuditInputBoundary auditInputBoundary;

    public AuditController(AuditInputBoundary auditInputBoundary) {
        this.auditInputBoundary = auditInputBoundary;
    }

    public void saveDocument(AuditDocument document) throws Exception {
        auditInputBoundary.saveDocument(document);
    }

    public void updateDocument(AuditDocument document) throws Exception {
        auditInputBoundary.updateDocument(document);
    }

    public void cancelDocument(AuditDocument document) throws Exception {
        auditInputBoundary.cancelDocument(document);
    }

    public void updateDocumentCanceled(AuditDocument document) throws Exception {
        auditInputBoundary.updateDocumentCanceled(document);
    }

    public Map<String, Object> findDocument(AuditDocument document) throws Exception {
        return auditInputBoundary.findDocument(document);
    }

    public List<Map<String, Object>> findSentErrorDocuments() throws Exception {
        return auditInputBoundary.findSentErrorDocuments();
    }

    public List<Map<String, Object>> findCanceledErrorDocuments() throws Exception {
        return auditInputBoundary.findCanceledErrorDocuments();
    }

}
