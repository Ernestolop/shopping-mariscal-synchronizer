package com.elopez.mariscal.synchronizer.modules.retriever.controller;

import java.util.List;
import java.util.Map;

import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.input.RetrieveInvoicesInputBoundary;

public class RetrieveNewInvoicesController {

    private RetrieveInvoicesInputBoundary retrieveInvoicesInputBoundary;

    public RetrieveNewInvoicesController(RetrieveInvoicesInputBoundary retrieveInvoicesInputBoundary) {
        this.retrieveInvoicesInputBoundary = retrieveInvoicesInputBoundary;
    }

    public List<Map<String, Object>> retrieveNewInvoices() throws Exception {
        return retrieveInvoicesInputBoundary.retrieveInvoices();
    }

}
