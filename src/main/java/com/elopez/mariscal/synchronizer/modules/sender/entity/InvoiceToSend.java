package com.elopez.mariscal.synchronizer.modules.sender.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InvoiceToSend {
    public String comprobante;
    public LocalDate fecha;
    public Currency moneda;
    public String cliente;
    public String ruc;
    public BigDecimal tipoCambio;
    public BigDecimal gravadas10;
    public BigDecimal gravadas5;
    public BigDecimal exentas;
    public BigDecimal total;
    public DocumentType tipo;
}
