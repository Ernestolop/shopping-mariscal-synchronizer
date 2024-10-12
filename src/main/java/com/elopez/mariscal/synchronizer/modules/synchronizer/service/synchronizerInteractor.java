package com.elopez.mariscal.synchronizer.modules.synchronizer.service;

import com.elopez.mariscal.synchronizer.modules.synchronizer.service.input.synchronizerInputBoundary;
import com.elopez.mariscal.synchronizer.modules.synchronizer.service.output.synchronizerOutputBoundary;

public class synchronizerInteractor implements synchronizerInputBoundary {

    private synchronizerOutputBoundary synchronizeInvoicesOutputBoundary;
    private synchronizerOutputBoundary synchronizeCancelledInvoicesOutputBoundary;
    private synchronizerOutputBoundary synchronizeCreditNotesOutputBoundary;
    private synchronizerOutputBoundary synchronizeCancelledCreditNotesOutputBoundary;

    public synchronizerInteractor(synchronizerOutputBoundary synchronizeInvoicesOutputBoundary,
            synchronizerOutputBoundary synchronizeCancelledInvoicesOutputBoundary,
            synchronizerOutputBoundary synchronizeCreditNotesOutputBoundary,
            synchronizerOutputBoundary synchronizeCancelledCreditNotesOutputBoundary) {
        this.synchronizeInvoicesOutputBoundary = synchronizeInvoicesOutputBoundary;
        this.synchronizeCancelledInvoicesOutputBoundary = synchronizeCancelledInvoicesOutputBoundary;
        this.synchronizeCreditNotesOutputBoundary = synchronizeCreditNotesOutputBoundary;
        this.synchronizeCancelledCreditNotesOutputBoundary = synchronizeCancelledCreditNotesOutputBoundary;
    }

    @Override
    public void synchronizeInvoices() throws Exception {
        synchronizeInvoicesOutputBoundary.synchronize();
    }

    @Override
    public void synchronizeCancelledInvoices() throws Exception {
        synchronizeCancelledInvoicesOutputBoundary.synchronize();
    }

    @Override
    public void synchronizeCreditNotes() throws Exception {
        synchronizeCreditNotesOutputBoundary.synchronize();
    }

    @Override
    public void synchronizeCancelledCreditNotes() throws Exception {
        synchronizeCancelledCreditNotesOutputBoundary.synchronize();
    }

}
