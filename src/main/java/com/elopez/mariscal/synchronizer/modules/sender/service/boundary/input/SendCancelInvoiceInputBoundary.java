package com.elopez.mariscal.synchronizer.modules.sender.service.boundary.input;

import com.elopez.mariscal.synchronizer.modules.sender.entity.InvoiceToCancel;

public interface SendCancelInvoiceInputBoundary {
    
    void cancelInvoice(InvoiceToCancel invoice) throws Exception;

}
