package com.elopez.mariscal.synchronizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

}
