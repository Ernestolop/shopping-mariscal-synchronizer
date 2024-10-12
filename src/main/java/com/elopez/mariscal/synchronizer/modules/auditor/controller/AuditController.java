package com.elopez.mariscal.synchronizer.modules.auditor.controller;

import java.util.List;
import java.util.Map;

import com.elopez.mariscal.synchronizer.modules.auditor.entity.Document;
import com.elopez.mariscal.synchronizer.modules.auditor.service.input.AuditInputBoundary;

public class AuditController {

    private AuditInputBoundary auditInputBoundary;

    public AuditController(AuditInputBoundary auditInputBoundary) {
        this.auditInputBoundary = auditInputBoundary;
    }

    public void saveDocument(Document document) throws Exception {
        auditInputBoundary.saveDocument(document);
    }

    public void cancelDocument(Document document) throws Exception {
        auditInputBoundary.cancelDocument(document);
    }

    public Map<String, Object> findDocument(Document document) throws Exception {
        return auditInputBoundary.findDocument(document);
    }

    public List<Map<String, Object>> findSentErrorDocuments() throws Exception {
        return auditInputBoundary.findSentErrorDocuments();
    }

    public List<Map<String, Object>> findCanceledErrorDocuments() throws Exception {
        return auditInputBoundary.findCanceledErrorDocuments();
    }

}
