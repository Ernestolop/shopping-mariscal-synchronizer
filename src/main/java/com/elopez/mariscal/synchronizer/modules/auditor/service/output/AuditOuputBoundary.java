package com.elopez.mariscal.synchronizer.modules.auditor.service.output;

import java.util.List;
import java.util.Map;

import com.elopez.mariscal.synchronizer.modules.auditor.entity.Document;

public interface AuditOuputBoundary {

    void saveDocument(Document document) throws Exception;

    void cancelDocument(Document document) throws Exception;

    Map<String, Object> findDocument(Document document) throws Exception;

    List<Map<String, Object>> findSentErrorDocuments() throws Exception;

    List<Map<String, Object>> findCanceledErrorDocuments() throws Exception;

}
