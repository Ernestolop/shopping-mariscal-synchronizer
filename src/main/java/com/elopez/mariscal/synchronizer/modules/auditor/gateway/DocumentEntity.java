package com.elopez.mariscal.synchronizer.modules.auditor.gateway;

import com.elopez.mariscal.synchronizer.modules.auditor.entity.DocumentType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "documents", indexes = {
        @Index(name = "external_id_document_type_index", columnList = "externalId, type")
}, uniqueConstraints = {
        @UniqueConstraint(name = "external_id_document_type_unique", columnNames = { "externalId", "type" })
})
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String externalId;

    @Column(nullable = false)
    private DocumentType type;

    @Column(nullable = false)
    private boolean sentSuccess;

    @Column(nullable = false)
    private int sentAttempts;

    @Column(nullable = false)
    private boolean canceled;

    private Boolean canceledSuccess;

    private Integer canceledAttempts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    public boolean isSentSuccess() {
        return sentSuccess;
    }

    public void setSentSuccess(boolean sentSuccess) {
        this.sentSuccess = sentSuccess;
    }

    public int getSentAttempts() {
        return sentAttempts;
    }

    public void setSentAttempts(int sentAttempts) {
        this.sentAttempts = sentAttempts;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public Boolean getCanceledSuccess() {
        return canceledSuccess;
    }

    public void setCanceledSuccess(Boolean canceledSuccess) {
        this.canceledSuccess = canceledSuccess;
    }

    public Integer getCanceledAttempts() {
        return canceledAttempts;
    }

    public void setCanceledAttempts(Integer canceledAttempts) {
        this.canceledAttempts = canceledAttempts;
    }

}
