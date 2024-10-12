package com.elopez.mariscal.synchronizer.modules.retriever.service;

import java.util.List;
import java.util.Map;

import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.input.RetrieveCreditNotesInputBoundary;
import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.output.RetrieveCreditNotesOutputBoundary;

public class RetrieveCreditNotesInteractor implements RetrieveCreditNotesInputBoundary {

    private RetrieveCreditNotesOutputBoundary outputBoundary;

    public RetrieveCreditNotesInteractor(RetrieveCreditNotesOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public List<Map<String, Object>> retrieveCreditNotes() throws Exception {
        return outputBoundary.retrieveCreditNotes();
    }

}
