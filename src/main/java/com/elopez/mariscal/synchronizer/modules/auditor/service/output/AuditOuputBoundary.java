package com.elopez.mariscal.synchronizer.modules.auditor.service.output;

import java.util.List;
import java.util.Map;

import com.elopez.mariscal.synchronizer.modules.auditor.entity.AuditDocument;

public interface AuditOuputBoundary {

    void saveDocument(AuditDocument document) throws Exception;

    void updateDocument(AuditDocument document) throws Exception;

    void cancelDocument(AuditDocument document) throws Exception;

    void updateDocumentCanceled(AuditDocument document) throws Exception;

    Map<String, Object> findDocument(AuditDocument document) throws Exception;

    List<Map<String, Object>> findSentErrorDocuments() throws Exception;

    List<Map<String, Object>> findCanceledErrorDocuments() throws Exception;

}
