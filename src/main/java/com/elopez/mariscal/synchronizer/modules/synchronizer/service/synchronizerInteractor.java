package com.elopez.mariscal.synchronizer.modules.synchronizer.service;

import com.elopez.mariscal.synchronizer.modules.synchronizer.service.input.synchronizerInputBoundary;
import com.elopez.mariscal.synchronizer.modules.synchronizer.service.output.synchronizerOutputBoundary;

public class synchronizerInteractor implements synchronizerInputBoundary {

    private synchronizerOutputBoundary synchronizeInvoicesOutputBoundary;
    private synchronizerOutputBoundary synchronizeCancelledInvoicesOutputBoundary;
    private synchronizerOutputBoundary synchronizeCreditNotesOutputBoundary;
    private synchronizerOutputBoundary synchronizeCancelledCreditNotesOutputBoundary;

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

    public void setSynchronizeInvoicesOutputBoundary(synchronizerOutputBoundary synchronizeInvoicesOutputBoundary) {
        this.synchronizeInvoicesOutputBoundary = synchronizeInvoicesOutputBoundary;
    }

    public void setSynchronizeCancelledInvoicesOutputBoundary(
            synchronizerOutputBoundary synchronizeCancelledInvoicesOutputBoundary) {
        this.synchronizeCancelledInvoicesOutputBoundary = synchronizeCancelledInvoicesOutputBoundary;
    }

    public void setSynchronizeCreditNotesOutputBoundary(synchronizerOutputBoundary synchronizeCreditNotesOutputBoundary) {
        this.synchronizeCreditNotesOutputBoundary = synchronizeCreditNotesOutputBoundary;
    }

    public void setSynchronizeCancelledCreditNotesOutputBoundary(
            synchronizerOutputBoundary synchronizeCancelledCreditNotesOutputBoundary) {
        this.synchronizeCancelledCreditNotesOutputBoundary = synchronizeCancelledCreditNotesOutputBoundary;
    }

}
