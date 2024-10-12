package com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.input;

import java.util.List;
import java.util.Map;

public interface RetrieverInputBoundary {
    
    List<Map<String, Object>> retrieveInvoices() throws Exception;

    List<Map<String, Object>> retrieveCreditNotes() throws Exception;

    List<Map<String, Object>> retrieveCancelledInvoices() throws Exception;

    List<Map<String, Object>> retrieveCancelledCreditNotes() throws Exception;

}
