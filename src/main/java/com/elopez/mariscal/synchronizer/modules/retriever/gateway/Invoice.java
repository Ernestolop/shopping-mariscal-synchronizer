package com.elopez.mariscal.synchronizer.modules.retriever.gateway;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "OINV")
public class Invoice {

    @Id
    @Column(name = "DocEntry")
    private Long docEntry;

    @Column(name = "DocDate")
    private Date docDate;

    @Column(name = "DocCur")
    private String docCur;

    @Column(name = "DocRate")
    private BigDecimal docRate;

    @Column(name = "CardName")
    private String cardName;

    @Column(name = "LicTradNum")
    private String licTradNum;

    @Column(name = "DocTotal")
    private BigDecimal docTotal;

    @Column(name = "DocTotalFC")
    private BigDecimal docTotalFC;

    @Column(name = "VatSum")
    private BigDecimal vatSum;

    @Column(name = "VatSumFC")
    private BigDecimal vatSumFC;

    @Column(name = "GrosProfit")
    private BigDecimal grosProfit;

    @Column(name = "GrosProfFC")
    private BigDecimal grosProfFC;

    @Column(name = "CANCELED")
    private String canceled;

    @Column(name = "DocStatus")
    private String docStatus;

    @Column(name = "DocType")
    private String docType;

    @Column(name = "U_EXX_FE_ClaAcc")
    private String uExxFEClaAcc;

    public Long getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(Long docEntry) {
        this.docEntry = docEntry;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public String getDocCur() {
        return docCur;
    }

    public void setDocCur(String docCur) {
        this.docCur = docCur;
    }

    public BigDecimal getDocRate() {
        return docRate;
    }

    public void setDocRate(BigDecimal docRate) {
        this.docRate = docRate;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getLicTradNum() {
        return licTradNum;
    }

    public void setLicTradNum(String licTradNum) {
        this.licTradNum = licTradNum;
    }

    public BigDecimal getDocTotal() {
        return docTotal;
    }

    public void setDocTotal(BigDecimal docTotal) {
        this.docTotal = docTotal;
    }

    public BigDecimal getDocTotalFC() {
        return docTotalFC;
    }

    public void setDocTotalFC(BigDecimal docTotalFC) {
        this.docTotalFC = docTotalFC;
    }

    public BigDecimal getVatSum() {
        return vatSum;
    }

    public void setVatSum(BigDecimal vatSum) {
        this.vatSum = vatSum;
    }

    public BigDecimal getVatSumFC() {
        return vatSumFC;
    }

    public void setVatSumFC(BigDecimal vatSumFC) {
        this.vatSumFC = vatSumFC;
    }

    public BigDecimal getGrosProfit() {
        return grosProfit;
    }

    public void setGrosProfit(BigDecimal grosProfit) {
        this.grosProfit = grosProfit;
    }

    public BigDecimal getGrosProfFC() {
        return grosProfFC;
    }

    public void setGrosProfFC(BigDecimal grosProfFC) {
        this.grosProfFC = grosProfFC;
    }

    public String getCanceled() {
        return canceled;
    }

    public void setCanceled(String canceled) {
        this.canceled = canceled;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getUExxFEClaAcc() {
        return uExxFEClaAcc;
    }

    public void setUExxFEClaAcc(String uExxFEClaAcc) {
        this.uExxFEClaAcc = uExxFEClaAcc;
    }

}
