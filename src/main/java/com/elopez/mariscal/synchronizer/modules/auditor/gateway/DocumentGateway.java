package com.elopez.mariscal.synchronizer.modules.auditor.gateway;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.elopez.mariscal.synchronizer.modules.auditor.entity.Document;
import com.elopez.mariscal.synchronizer.modules.auditor.errors.DocumentNotFound;
import com.elopez.mariscal.synchronizer.modules.auditor.service.output.AuditOuputBoundary;

@Service
public class DocumentGateway
        implements AuditOuputBoundary {

    @Autowired
    private DocumentRepository documentRepository;

    @Value("${auditor.sent.attempts}")
    private int sentAttempts;

    @Value("${auditor.canceled.attempts}")
    private int canceledAttempts;

    @Override
    public void saveDocument(Document document) {
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setExternalId(document.id);
        documentEntity.setType(document.type);
        documentEntity.setSentSuccess(document.hasError);
        documentEntity.setSentAttempts(1);
        documentEntity.setCanceled(false);
        documentEntity.setCanceledSuccess(null);
        documentEntity.setCanceledAttempts(null);
        documentRepository.save(documentEntity);
    }

    @Override
    public void cancelDocument(Document document) throws DocumentNotFound {
        Optional<DocumentEntity> OptionalDocumentEntity = documentRepository.findByExternalIdAndType(document.id,
                document.type);
        if (OptionalDocumentEntity.isPresent()) {
            DocumentEntity documentEntity = OptionalDocumentEntity.get();
            documentEntity.setCanceled(true);
            documentEntity.setCanceledSuccess(document.hasError);
            documentEntity.setCanceledAttempts(1);
            documentRepository.save(documentEntity);
            return;
        }
        throw new DocumentNotFound("Document with id " + document.id + " and type " + document.type.name() + " not found");
    }

    @Override
    public Map<String, Object> findDocument(Document document) throws DocumentNotFound {
        Optional<DocumentEntity> OptionalDocumentEntity = documentRepository.findByExternalIdAndType(document.id,
                document.type);
        if (OptionalDocumentEntity.isPresent()) {
            DocumentEntity documentEntity = OptionalDocumentEntity.get();
            Map<String, Object> documentMap = new HashMap<>();
            documentMap.put("id", documentEntity.getId());
            documentMap.put("externalId", documentEntity.getExternalId());
            documentMap.put("type", documentEntity.getType());
            documentMap.put("sentSuccess", documentEntity.isSentSuccess());
            documentMap.put("sentAttempts", documentEntity.getSentAttempts());
            documentMap.put("canceled", documentEntity.isCanceled());
            documentMap.put("canceledSuccess", documentEntity.getCanceledSuccess());
            documentMap.put("canceledAttempts", documentEntity.getCanceledAttempts());
            return documentMap;
        }
        throw new DocumentNotFound("Document with id " + document.id + " and type " + document.type.name() + " not found");
    }

    @Override
    public List<Map<String, Object>> findSentErrorDocuments() {
        List<DocumentEntity> documentEntities = documentRepository
                .findBySentSuccessFalseAndSentAttemptsLessThan(sentAttempts);
        List<Map<String, Object>> documentMaps = new ArrayList<>();
        for (DocumentEntity documentEntity : documentEntities) {
            Map<String, Object> documentMap = new HashMap<>();
            documentMap.put("id", documentEntity.getId());
            documentMap.put("externalId", documentEntity.getExternalId());
            documentMap.put("type", documentEntity.getType());
            documentMap.put("sentSuccess", documentEntity.isSentSuccess());
            documentMap.put("sentAttempts", documentEntity.getSentAttempts());
            documentMap.put("canceled", documentEntity.isCanceled());
            documentMap.put("canceledSuccess", documentEntity.getCanceledSuccess());
            documentMap.put("canceledAttempts", documentEntity.getCanceledAttempts());
            documentMaps.add(documentMap);
        }
        return documentMaps;
    }

    @Override
    public List<Map<String, Object>> findCanceledErrorDocuments() {
        List<DocumentEntity> documentEntities = documentRepository
                .findByCanceledSuccessFalseAndCanceledAttemptsLessThan(canceledAttempts);
        List<Map<String, Object>> documentMaps = new ArrayList<>();
        for (DocumentEntity documentEntity : documentEntities) {
            Map<String, Object> documentMap = new HashMap<>();
            documentMap.put("id", documentEntity.getId());
            documentMap.put("externalId", documentEntity.getExternalId());
            documentMap.put("type", documentEntity.getType());
            documentMap.put("sentSuccess", documentEntity.isSentSuccess());
            documentMap.put("sentAttempts", documentEntity.getSentAttempts());
            documentMap.put("canceled", documentEntity.isCanceled());
            documentMap.put("canceledSuccess", documentEntity.getCanceledSuccess());
            documentMap.put("canceledAttempts", documentEntity.getCanceledAttempts());
            documentMaps.add(documentMap);
        }
        return documentMaps;
    }

}
