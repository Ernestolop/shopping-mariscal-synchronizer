package com.elopez.mariscal.synchronizer.modules.sender.service.boundaries.input;

import com.elopez.mariscal.synchronizer.modules.sender.entity.InvoiceToSend;

public interface SendInvoiceInputBoundary {

    void sendInvoice(InvoiceToSend invoice) throws Exception;

}
