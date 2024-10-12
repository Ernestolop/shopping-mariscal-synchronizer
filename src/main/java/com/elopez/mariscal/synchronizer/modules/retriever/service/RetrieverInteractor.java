package com.elopez.mariscal.synchronizer.modules.retriever.service;

import java.util.List;
import java.util.Map;

import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.input.RetrieverInputBoundary;
import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.output.RetrieverOutputBoundary;

public class RetrieverInteractor implements RetrieverInputBoundary {

    private RetrieverOutputBoundary invoicesOutputBoundary;
    private RetrieverOutputBoundary creditNotesOutputBoundary;
    private RetrieverOutputBoundary cancelledInvoicesOutputBoundary;
    private RetrieverOutputBoundary cancelledCreditNotesOutputBoundary;

    @Override
    public List<Map<String, Object>> retrieveInvoices() throws Exception {
        return invoicesOutputBoundary.retrieve();
    }

    @Override
    public List<Map<String, Object>> retrieveCreditNotes() throws Exception {
        return creditNotesOutputBoundary.retrieve();
    }

    @Override
    public List<Map<String, Object>> retrieveCancelledInvoices() throws Exception {
        return cancelledInvoicesOutputBoundary.retrieve();
    }

    @Override
    public List<Map<String, Object>> retrieveCancelledCreditNotes() throws Exception {
        return cancelledCreditNotesOutputBoundary.retrieve();
    }

    public void setInvoicesOutputBoundary(RetrieverOutputBoundary invoicesOutputBoundary) {
        this.invoicesOutputBoundary = invoicesOutputBoundary;
    }

    public void setCreditNotesOutputBoundary(RetrieverOutputBoundary creditNotesOutputBoundary) {
        this.creditNotesOutputBoundary = creditNotesOutputBoundary;
    }

    public void setCancelledInvoicesOutputBoundary(RetrieverOutputBoundary cancelledInvoicesOutputBoundary) {
        this.cancelledInvoicesOutputBoundary = cancelledInvoicesOutputBoundary;
    }

    public void setCancelledCreditNotesOutputBoundary(RetrieverOutputBoundary cancelledCreditNotesOutputBoundary) {
        this.cancelledCreditNotesOutputBoundary = cancelledCreditNotesOutputBoundary;
    }

}
