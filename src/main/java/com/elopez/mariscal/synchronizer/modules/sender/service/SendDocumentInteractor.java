package com.elopez.mariscal.synchronizer.modules.sender.service;

import java.math.BigDecimal;

import com.elopez.mariscal.synchronizer.modules.sender.entity.Currency;
import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentType;
import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentToSend;
import com.elopez.mariscal.synchronizer.modules.sender.service.boundary.input.SendDocumentInputBoundary;
import com.elopez.mariscal.synchronizer.modules.sender.service.boundary.output.SendDocumentOutputBoundary;

public class SendDocumentInteractor implements SendDocumentInputBoundary {

    private SendDocumentOutputBoundary sendDocumentOutputBoundary;

    public SendDocumentInteractor(SendDocumentOutputBoundary sendDocumentOutputBoundary) {
        this.sendDocumentOutputBoundary = sendDocumentOutputBoundary;
    }

    @Override
    public void sendDocument(DocumentToSend document) throws Exception {
        validateDocument(document);
        sendDocumentOutputBoundary.sendDocument(document);
    }

    private void validateDocument(DocumentToSend document) {
        validateNumber(document.comprobante);
        validateDocumentType(document.tipo);
        validateIssuedDate(document.fecha);
        validateExchangeRate(document.moneda, document.tipoCambio);
        validateCustomer(document.cliente, document.ruc);
        validateAmounts(document.gravadas10, document.gravadas5, document.exentas, document.total);
    }

    private void validateNumber(String documentNumber) {
        if (documentNumber == null) {
            throw new Error("El número de documento es requerido");
        } else if (!documentNumber.matches("\\d{3}-\\d{3}-\\d{7}")) {
            throw new Error("El número de documento no es válido");
        }
    }

    private void validateDocumentType(DocumentType type) {
        if (type == null) {
            throw new Error("El tipo de documento es requerido");
        } else if (!type.equals(DocumentType.FACT) && !type.equals(DocumentType.NCR)) {
            throw new Error("El tipo de documento para envio es invalido");
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