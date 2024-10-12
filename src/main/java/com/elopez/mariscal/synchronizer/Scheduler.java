package com.elopez.mariscal.synchronizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.elopez.mariscal.synchronizer.modules.synchronizer.controller.synchronizerController;
import com.elopez.mariscal.synchronizer.modules.synchronizer.gateway.SynchronizeCancelledCreditNotesGateway;
import com.elopez.mariscal.synchronizer.modules.synchronizer.gateway.SynchronizeCancelledInvoicesGateway;
import com.elopez.mariscal.synchronizer.modules.synchronizer.gateway.SynchronizeCreditNotesGateway;
import com.elopez.mariscal.synchronizer.modules.synchronizer.gateway.SynchronizeInvoicesGateway;
import com.elopez.mariscal.synchronizer.modules.synchronizer.service.synchronizerInteractor;

@Component
public class Scheduler {

    private Logger logger = LoggerFactory.getLogger(Scheduler.class);

    @Value("${invoices.synchronization.cron}")
    private String invoicesSynchronizationCron;

    @Value("${credit.notes.synchronization.cron}")
    private String creditNotesSynchronizationCron;

    @Value("${cancelled.invoice.synchronization.cron}")
    private String cancelledInvoicesSynchronizationCron;

    @Value("${cancelled.credit.notes.synchronization.cron}")
    private String cancelledCreditNotesSynchronizationCron;

    @Scheduled(cron = "${invoices.synchronization.cron}")
    public void synchronizeInvoices() {
        var synchronizer = getInvoicesSynchronizer();
        try {
            synchronizer.synchronizeInvoices();
        } catch (Exception e) {
            logger.error("Error synchronizing invoices", e);
        }
    }

    @Scheduled(cron = "${credit.notes.synchronization.cron}")
    public void synchronizeCreditNotes() {
        var synchronizer = getCreditNotesSynchronizer();
        try {
            synchronizer.synchronizeCreditNotes();
        } catch (Exception e) {
            logger.error("Error synchronizing credit notes", e);
        }
    }

    @Scheduled(cron = "${cancelled.invoice.synchronization.cron}")
    public void synchronizeCancelledInvoices() {
        var synchronizer = getCancelledInvoicesSynchronizer();
        try {
            synchronizer.synchronizeCancelledInvoices();
        } catch (Exception e) {
            logger.error("Error synchronizing cancelled invoices", e);
        }
    }

    @Scheduled(cron = "${cancelled.credit.notes.synchronization.cron}")
    public void synchronizeCancelledCreditNotes() {
        var synchronizer = getCancelledCreditNotesSynchronizer();
        try {
            synchronizer.synchronizeCancelledCreditNotes();
        } catch (Exception e) {
            logger.error("Error synchronizing cancelled credit notes", e);
        }
    }

    private synchronizerController getInvoicesSynchronizer() {
        var gateway = new SynchronizeInvoicesGateway();
        var interactor = new synchronizerInteractor();
        interactor.setSynchronizeInvoicesOutputBoundary(gateway);
        var synchronizerController = new synchronizerController(interactor);
        return synchronizerController;
    }

    private synchronizerController getCreditNotesSynchronizer() {
        var gateway = new SynchronizeCreditNotesGateway();
        var interactor = new synchronizerInteractor();
        interactor.setSynchronizeCreditNotesOutputBoundary(gateway);
        var synchronizerController = new synchronizerController(interactor);
        return synchronizerController;
    }

    private synchronizerController getCancelledInvoicesSynchronizer() {
        var gateway = new SynchronizeCancelledInvoicesGateway();
        var interactor = new synchronizerInteractor();
        interactor.setSynchronizeCancelledInvoicesOutputBoundary(gateway);
        var synchronizerController = new synchronizerController(interactor);
        return synchronizerController;
    }

    private synchronizerController getCancelledCreditNotesSynchronizer() {
        var gateway = new SynchronizeCancelledCreditNotesGateway();
        var interactor = new synchronizerInteractor();
        interactor.setSynchronizeCancelledCreditNotesOutputBoundary(gateway);
        var synchronizerController = new synchronizerController(interactor);
        return synchronizerController;
    }

}
