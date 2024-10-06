package com.elopez.mariscal.synchronizer.modules.sender.service.boundary.input;

import com.elopez.mariscal.synchronizer.modules.sender.entity.InvoiceToSend;

public interface SendInvoiceInputBoundary {

    void sendInvoice(InvoiceToSend invoice) throws Exception;

}
