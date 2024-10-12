package com.elopez.mariscal.synchronizer.modules.auditor.gateway;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elopez.mariscal.synchronizer.modules.auditor.entity.DocumentType;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {

    Optional<DocumentEntity> findByExternalIdAndType(String externalId, DocumentType type);

    List<DocumentEntity> findBySentSuccessFalseAndSentAttemptsLessThan(int attempts);

    List<DocumentEntity> findByCanceledSuccessFalseAndCanceledAttemptsLessThan(int attempts);
    
}
