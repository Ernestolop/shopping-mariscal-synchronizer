package com.elopez.mariscal.synchronizer.modules.retriever.controller;

import java.util.List;
import java.util.Map;

import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.input.RetrieveCancelledInvoicesInputBoundary;

public class RetrieveCancelledInvoicesController {

    private RetrieveCancelledInvoicesInputBoundary retrieveCancelledInvoicesInputBoundary;

    public RetrieveCancelledInvoicesController(RetrieveCancelledInvoicesInputBoundary retrieveCancelledInvoicesInputBoundary) {
        this.retrieveCancelledInvoicesInputBoundary = retrieveCancelledInvoicesInputBoundary;
    }

    public List<Map<String, Object>> retrieveCancelledInvoices() throws Exception {
        return retrieveCancelledInvoicesInputBoundary.retrieveCancelledInvoices();
    }
    
}
