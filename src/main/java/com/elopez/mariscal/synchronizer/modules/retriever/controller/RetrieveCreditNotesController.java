package com.elopez.mariscal.synchronizer.modules.retriever.controller;

import java.util.List;
import java.util.Map;

import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.input.RetrieveCreditNotesInputBoundary;

public class RetrieveCreditNotesController {

    private RetrieveCreditNotesInputBoundary retrieveCreditNotesInputBoundary;

    public RetrieveCreditNotesController(RetrieveCreditNotesInputBoundary retrieveCreditNotesInputBoundary) {
        this.retrieveCreditNotesInputBoundary = retrieveCreditNotesInputBoundary;
    }

    public List<Map<String, Object>> retrieveCreditNotes() throws Exception {
        return retrieveCreditNotesInputBoundary.retrieveCreditNotes();
    }

}
