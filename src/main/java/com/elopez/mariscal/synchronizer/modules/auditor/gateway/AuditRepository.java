package com.elopez.mariscal.synchronizer.modules.auditor.gateway;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elopez.mariscal.synchronizer.modules.auditor.entity.AuditDocumentType;

@Repository
public interface AuditRepository extends JpaRepository<AuditDocumentEntity, Long> {

    Optional<AuditDocumentEntity> findByExternalIdAndType(String externalId, AuditDocumentType type);

    List<AuditDocumentEntity> findBySentSuccessFalseAndSentAttemptsLessThan(int attempts);

    List<AuditDocumentEntity> findByCanceledSuccessFalseAndCanceledAttemptsLessThan(int attempts);
    
}
