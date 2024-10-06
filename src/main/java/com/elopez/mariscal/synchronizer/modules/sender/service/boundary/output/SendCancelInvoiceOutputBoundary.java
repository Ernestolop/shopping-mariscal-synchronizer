package com.elopez.mariscal.synchronizer.modules.sender.service.boundary.output;

import com.elopez.mariscal.synchronizer.modules.sender.entity.InvoiceToCancel;

public interface SendCancelInvoiceOutputBoundary {

    void sendCancelInvoice(InvoiceToCancel invoice) throws Exception;
}
