package com.elopez.mariscal.synchronizer.modules.retriever.controller;

import java.util.List;
import java.util.Map;

import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.input.RetrieveCreditNotesInputBoundary;

public class RetrieveNewCreditNotesController {

    private RetrieveCreditNotesInputBoundary retrieveCreditNotesInputBoundary;

    public RetrieveNewCreditNotesController(RetrieveCreditNotesInputBoundary retrieveCreditNotesInputBoundary) {
        this.retrieveCreditNotesInputBoundary = retrieveCreditNotesInputBoundary;
    }

    public List<Map<String, Object>> retrieveNewCreditNotes() throws Exception {
        return retrieveCreditNotesInputBoundary.retrieveCreditNotes();
    }

}
