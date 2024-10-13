package com.elopez.mariscal.synchronizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.elopez.mariscal.synchronizer.modules.auditor.gateway.AuditGateway;
import com.elopez.mariscal.synchronizer.modules.retriever.gateway.RetrieveInvoicesJpa;
import com.elopez.mariscal.synchronizer.modules.retriever.gateway.RetrieveCancelledInvoicesJpa;
import com.elopez.mariscal.synchronizer.modules.retriever.gateway.RetrieveCreditNotesJpa;
import com.elopez.mariscal.synchronizer.modules.retriever.gateway.RetrieveCancelledCreditNotesJpa;
import com.elopez.mariscal.synchronizer.modules.synchronizer.controller.synchronizerController;
import com.elopez.mariscal.synchronizer.modules.synchronizer.gateway.cancelled.SynchronizeCancelledCreditNotesGateway;
import com.elopez.mariscal.synchronizer.modules.synchronizer.gateway.cancelled.SynchronizeCancelledInvoicesGateway;
import com.elopez.mariscal.synchronizer.modules.synchronizer.gateway.sent.SynchronizeCreditNotesGateway;
import com.elopez.mariscal.synchronizer.modules.synchronizer.gateway.sent.SynchronizeInvoicesGateway;
import com.elopez.mariscal.synchronizer.modules.synchronizer.service.synchronizerInteractor;

@Component
public class Scheduler {

    @Autowired
    private RetrieveInvoicesJpa retrieveInvoicesJpa;
    @Autowired
    private RetrieveCreditNotesJpa retrieveCreditNotesJpa;
    @Autowired
    private RetrieveCancelledInvoicesJpa retrieveCancelledInvoicesJpa;
    @Autowired
    private RetrieveCancelledCreditNotesJpa retrieveCancelledCreditNotesJpa;

    @Autowired
    private AuditGateway documentGateway;

    private Logger logger = LoggerFactory.getLogger(Scheduler.class);

    @Scheduled(cron = "${invoices.cron}")
    public void synchronizeInvoices() {
        var synchronizer = getInvoicesSynchronizer();
        try {
            synchronizer.synchronizeInvoices();
        } catch (Exception e) {
            logger.error("Error synchronizing invoices", e);
        }
    }

    @Scheduled(cron = "${credit.notes.cron}")
    public void synchronizeCreditNotes() {
        var synchronizer = getCreditNotesSynchronizer();
        try {
            synchronizer.synchronizeCreditNotes();
        } catch (Exception e) {
            logger.error("Error synchronizing credit notes", e);
        }
    }

    @Scheduled(cron = "${cancelled.invoice.cron}")
    public void synchronizeCancelledInvoices() {
        var synchronizer = getCancelledInvoicesSynchronizer();
        try {
            synchronizer.synchronizeCancelledInvoices();
        } catch (Exception e) {
            logger.error("Error synchronizing cancelled invoices", e);
        }
    }

    @Scheduled(cron = "${cancelled.credit.notes.cron}")
    public void synchronizeCancelledCreditNotes() {
        var synchronizer = getCancelledCreditNotesSynchronizer();
        try {
            synchronizer.synchronizeCancelledCreditNotes();
        } catch (Exception e) {
            logger.error("Error synchronizing cancelled credit notes", e);
        }
    }

    private synchronizerController getInvoicesSynchronizer() {
        var gateway = new SynchronizeInvoicesGateway(retrieveInvoicesJpa, documentGateway);
        var interactor = new synchronizerInteractor();
        interactor.setSynchronizeInvoicesOutputBoundary(gateway);
        var synchronizerController = new synchronizerController(interactor);
        return synchronizerController;
    }

    private synchronizerController getCreditNotesSynchronizer() {
        var gateway = new SynchronizeCreditNotesGateway(retrieveCreditNotesJpa, documentGateway);
        var interactor = new synchronizerInteractor();
        interactor.setSynchronizeCreditNotesOutputBoundary(gateway);
        var synchronizerController = new synchronizerController(interactor);
        return synchronizerController;
    }

    private synchronizerController getCancelledInvoicesSynchronizer() {
        var gateway = new SynchronizeCancelledInvoicesGateway(retrieveCancelledInvoicesJpa, documentGateway);
        var interactor = new synchronizerInteractor();
        interactor.setSynchronizeCancelledInvoicesOutputBoundary(gateway);
        var synchronizerController = new synchronizerController(interactor);
        return synchronizerController;
    }

    private synchronizerController getCancelledCreditNotesSynchronizer() {
        var gateway = new SynchronizeCancelledCreditNotesGateway(retrieveCancelledCreditNotesJpa, documentGateway);
        var interactor = new synchronizerInteractor();
        interactor.setSynchronizeCancelledCreditNotesOutputBoundary(gateway);
        var synchronizerController = new synchronizerController(interactor);
        return synchronizerController;
    }

}
