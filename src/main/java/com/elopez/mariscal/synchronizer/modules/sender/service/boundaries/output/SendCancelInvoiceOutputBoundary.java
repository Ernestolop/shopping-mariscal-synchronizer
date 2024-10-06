package com.elopez.mariscal.synchronizer.modules.sender.service.boundaries.output;

import com.elopez.mariscal.synchronizer.modules.sender.entity.InvoiceToCancel;

public interface SendCancelInvoiceOutputBoundary {

    void sendCancelInvoice(InvoiceToCancel invoice) throws Exception;
}
