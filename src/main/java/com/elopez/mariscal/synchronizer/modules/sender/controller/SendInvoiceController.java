package com.elopez.mariscal.synchronizer.modules.sender.controller;

import com.elopez.mariscal.synchronizer.modules.sender.entity.InvoiceToSend;
import com.elopez.mariscal.synchronizer.modules.sender.service.boundaries.input.SendInvoiceInputBoundary;

public class SendInvoiceController {

    private SendInvoiceInputBoundary sendInvoiceInputBoundary;

    public SendInvoiceController(SendInvoiceInputBoundary sendInvoiceInputBoundary) {
        this.sendInvoiceInputBoundary = sendInvoiceInputBoundary;
    }

    public void sendInvoice(InvoiceToSend invoice) throws Exception {
        sendInvoiceInputBoundary.sendInvoice(invoice);
    }

}
