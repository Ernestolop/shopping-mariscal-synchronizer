package com.elopez.mariscal.synchronizer.modules.retriever.controller;

import java.util.List;
import java.util.Map;

import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.input.RetrieverInputBoundary;

public class RetrieveController {

    private RetrieverInputBoundary retrieveInvoicesInputBoundary;
    private RetrieverInputBoundary retrieveCreditNotesInputBoundary;
    private RetrieverInputBoundary retrieveCancelledInvoicesInputBoundary;
    private RetrieverInputBoundary retrieveCancelledCreditNotesInputBoundary;

    public List<Map<String, Object>> retrieveInvoices() throws Exception {
        return retrieveInvoicesInputBoundary.retrieveInvoices();
    }

    public List<Map<String, Object>> retrieveCreditNotes() throws Exception {
        return retrieveCreditNotesInputBoundary.retrieveCreditNotes();
    }

    public List<Map<String, Object>> retrieveCancelledInvoices() throws Exception {
        return retrieveCancelledInvoicesInputBoundary.retrieveCancelledInvoices();
    }

    public List<Map<String, Object>> retrieveCancelledCreditNotes() throws Exception {
        return retrieveCancelledCreditNotesInputBoundary.retrieveCancelledCreditNotes();
    }

    public void setRetrieveInvoicesInputBoundary(
            RetrieverInputBoundary retrieveInvoicesInputBoundary) {
        this.retrieveInvoicesInputBoundary = retrieveInvoicesInputBoundary;
    }

    public void setRetrieveCreditNotesInputBoundary(
            RetrieverInputBoundary retrieveCreditNotesInputBoundary) {
        this.retrieveCreditNotesInputBoundary = retrieveCreditNotesInputBoundary;
    }

    public void setRetrieveCancelledInvoicesInputBoundary(
            RetrieverInputBoundary retrieveCancelledInvoicesInputBoundary) {
        this.retrieveCancelledInvoicesInputBoundary = retrieveCancelledInvoicesInputBoundary;
    }

    public void setRetrieveCancelledCreditNotesInputBoundary(
            RetrieverInputBoundary retrieveCancelledCreditNotesInputBoundary) {
        this.retrieveCancelledCreditNotesInputBoundary = retrieveCancelledCreditNotesInputBoundary;
    }

}
