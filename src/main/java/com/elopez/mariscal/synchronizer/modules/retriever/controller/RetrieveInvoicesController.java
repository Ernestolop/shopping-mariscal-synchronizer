package com.elopez.mariscal.synchronizer.modules.retriever.controller;

import java.util.List;
import java.util.Map;

import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.input.RetrieveInvoicesInputBoundary;

public class RetrieveInvoicesController {

    private RetrieveInvoicesInputBoundary retrieveInvoicesInputBoundary;

    public RetrieveInvoicesController(RetrieveInvoicesInputBoundary retrieveInvoicesInputBoundary) {
        this.retrieveInvoicesInputBoundary = retrieveInvoicesInputBoundary;
    }

    public List<Map<String, Object>> retrieveInvoices() throws Exception {
        return retrieveInvoicesInputBoundary.retrieveInvoices();
    }

}
