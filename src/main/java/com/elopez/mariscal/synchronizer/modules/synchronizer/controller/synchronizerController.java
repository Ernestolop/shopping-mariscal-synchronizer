package com.elopez.mariscal.synchronizer.modules.synchronizer.controller;

import com.elopez.mariscal.synchronizer.modules.synchronizer.service.input.synchronizerInputBoundary;

public class synchronizerController {

    private synchronizerInputBoundary synchronizerInputBoundary;

    public synchronizerController(synchronizerInputBoundary synchronizerInputBoundary) {
        this.synchronizerInputBoundary = synchronizerInputBoundary;
    }

    public void synchronizeInvoices() throws Exception {
        synchronizerInputBoundary.synchronizeInvoices();
    }

    public void synchronizeCancelledInvoices() throws Exception {
        synchronizerInputBoundary.synchronizeCancelledInvoices();
    }

    public void synchronizeCreditNotes() throws Exception {
        synchronizerInputBoundary.synchronizeCreditNotes();
    }

    public void synchronizeCancelledCreditNotes() throws Exception {
        synchronizerInputBoundary.synchronizeCancelledCreditNotes();
    }

}
