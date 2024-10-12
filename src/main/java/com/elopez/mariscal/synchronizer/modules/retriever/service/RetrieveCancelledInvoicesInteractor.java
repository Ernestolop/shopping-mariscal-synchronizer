package com.elopez.mariscal.synchronizer.modules.retriever.service;

import java.util.List;
import java.util.Map;

import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.input.RetrieveCancelledInvoicesInputBoundary;
import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.output.RetrieveCancelledInvoicesOutputBoundary;

public class RetrieveCancelledInvoicesInteractor implements RetrieveCancelledInvoicesInputBoundary {

    private RetrieveCancelledInvoicesOutputBoundary outputBoundary;

    public RetrieveCancelledInvoicesInteractor(RetrieveCancelledInvoicesOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public List<Map<String, Object>> retrieveCancelledInvoices() throws Exception {
        return outputBoundary.retrieveCancelledInvoices();
    }
    
}
