package com.elopez.mariscal.synchronizer.modules.retriever.service;

import java.util.List;
import java.util.Map;

import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.input.RetrieveInvoicesInputBoundary;
import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.output.RetrieveInvoicesOutputBoundary;

public class RetrieveInvoicesInteractor implements RetrieveInvoicesInputBoundary {

    private RetrieveInvoicesOutputBoundary outputBoundary;

    public RetrieveInvoicesInteractor(RetrieveInvoicesOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    @Override
    public List<Map<String, Object>> retrieveInvoices() throws Exception {
        return outputBoundary.retrieveInvoices();
    }

}
