package com.elopez.mariscal.synchronizer.modules.synchronizer.service.input;

public interface synchronizerInputBoundary {

    void synchronizeInvoices() throws Exception;

    void synchronizeCancelledInvoices() throws Exception;

    void synchronizeCreditNotes() throws Exception;

    void synchronizeCancelledCreditNotes() throws Exception;

}
