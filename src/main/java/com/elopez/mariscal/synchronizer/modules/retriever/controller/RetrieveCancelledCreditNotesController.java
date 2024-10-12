package com.elopez.mariscal.synchronizer.modules.retriever.controller;

import java.util.List;
import java.util.Map;

import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.input.RetrieveCancelledCreditNotesInputBoundary;

public class RetrieveCancelledCreditNotesController {

    private RetrieveCancelledCreditNotesInputBoundary retrieveCancelledCreditNotesInputBoundary;

    public RetrieveCancelledCreditNotesController(
            RetrieveCancelledCreditNotesInputBoundary retrieveCancelledCreditNotesInputBoundary) {
        this.retrieveCancelledCreditNotesInputBoundary = retrieveCancelledCreditNotesInputBoundary;
    }

    public List<Map<String, Object>> retrieveCancelledCreditNotes() throws Exception {
        return retrieveCancelledCreditNotesInputBoundary.retrieveCancelledCreditNotes();
    }

}
