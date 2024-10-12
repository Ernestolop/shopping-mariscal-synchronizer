package com.elopez.mariscal.synchronizer.modules.retriever.service;

import java.util.List;
import java.util.Map;

import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.input.RetrieveCancelledCreditNotesInputBoundary;
import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.output.RetrieveCancelledCreditNotesOutputBoundary;

public class RetrieveCancelledCreditNotesInteractor implements RetrieveCancelledCreditNotesInputBoundary {

    private RetrieveCancelledCreditNotesOutputBoundary outputBoundary;

    public RetrieveCancelledCreditNotesInteractor(RetrieveCancelledCreditNotesOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public List<Map<String, Object>> retrieveCancelledCreditNotes() throws Exception {
        return outputBoundary.retrieveCancelledCreditNotes();
    }

}