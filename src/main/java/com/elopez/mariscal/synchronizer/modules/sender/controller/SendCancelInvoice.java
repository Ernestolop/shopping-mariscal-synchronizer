package com.elopez.mariscal.synchronizer.modules.sender.controller;

import com.elopez.mariscal.synchronizer.modules.sender.entity.InvoiceToCancel;
import com.elopez.mariscal.synchronizer.modules.sender.service.boundaries.input.SendCancelInvoiceInputBoundary;

public class SendCancelInvoice {

    private SendCancelInvoiceInputBoundary sendCancelInvoiceInputBoundary;

    public SendCancelInvoice(SendCancelInvoiceInputBoundary sendCancelInvoiceInteractor) {
        this.sendCancelInvoiceInputBoundary = sendCancelInvoiceInteractor;
    }

    public void sendCancelInvoice(InvoiceToCancel invoice) throws Exception {
        sendCancelInvoiceInputBoundary.cancelInvoice(invoice);
    }

}
