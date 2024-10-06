package com.elopez.mariscal.synchronizer.modules.sender.service;

import java.math.BigDecimal;

import com.elopez.mariscal.synchronizer.modules.sender.entity.Currency;
import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentType;
import com.elopez.mariscal.synchronizer.modules.sender.entity.InvoiceToSend;
import com.elopez.mariscal.synchronizer.modules.sender.service.boundaries.input.SendInvoiceInputBoundary;
import com.elopez.mariscal.synchronizer.modules.sender.service.boundaries.output.SendInvoiceOutputBoundary;

public class SendInvoiceInteractor implements SendInvoiceInputBoundary {

    private SendInvoiceOutputBoundary sendInvoiceOutputBoundary;

    public SendInvoiceInteractor(SendInvoiceOutputBoundary sendInvoiceOutputBoundary) {
        this.sendInvoiceOutputBoundary = sendInvoiceOutputBoundary;
    }

    @Override
    public void sendInvoice(InvoiceToSend invoice) throws Exception {
        validateInvoice(invoice);
        invoice.tipo = DocumentType.FACT;
        sendInvoiceOutputBoundary.sendInvoice(invoice);
    }

    private void validateInvoice(InvoiceToSend invoice) {
        validateNumber(invoice.comprobante);
        validateIssuedDate(invoice.fecha);
        validateExchangeRate(invoice.moneda, invoice.tipoCambio);
        validateCustomer(invoice.cliente, invoice.ruc);
        validateAmounts(invoice.gravadas10, invoice.gravadas5, invoice.exentas, invoice.total);
    }

    private void validateNumber(String documentNumber) {
        if (documentNumber == null) {
            throw new Error("El número de documento es requerido");
        } else if (!documentNumber.matches("\\d{3}-\\d{3}-\\d{7}")) {
            throw new Error("El número de documento no es válido");
        }
    }

    private void validateIssuedDate(java.time.LocalDate issuedDate) {
        if (issuedDate == null) {
            throw new Error("La fecha de emisión es requerida");
        }
    }

    private void validateExchangeRate(Currency currency, BigDecimal exchangeRate) {
        if (currency == null) {
            throw new Error("La moneda es requerida");
        } else if (!currency.isLocal() && exchangeRate == null) {
            throw new Error("El tipo de cambio es requerido para monedas extranjeras");
        }
    }

    private void validateCustomer(String customer, String ruc) {
        if (customer == null) {
            throw new Error("El nombre del cliente es requerido");
        } else if (ruc == null) {
            throw new Error("El RUC del cliente es requerido");
        }
    }

    private void validateAmounts(BigDecimal gravadas10, BigDecimal gravadas5, BigDecimal exentas, BigDecimal total) {
        if (gravadas10 == null) {
            throw new Error("El monto gravado al 10% es requerido");
        } else if (gravadas5 == null) {
            throw new Error("El monto gravado al 5% es requerido");
        } else if (exentas == null) {
            throw new Error("El monto exento es requerido");
        } else if (total == null) {
            throw new Error("El monto total es requerido");
        }
    }

}