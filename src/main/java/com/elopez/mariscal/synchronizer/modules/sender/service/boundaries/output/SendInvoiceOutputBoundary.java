package com.elopez.mariscal.synchronizer.modules.sender.service.boundaries.output;

import com.elopez.mariscal.synchronizer.modules.sender.entity.InvoiceToSend;

public interface SendInvoiceOutputBoundary {

    void sendInvoice(InvoiceToSend invoice) throws Exception;

}
