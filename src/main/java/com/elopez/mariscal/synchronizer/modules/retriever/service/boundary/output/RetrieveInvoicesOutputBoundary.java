package com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.output;

import java.util.List;
import java.util.Map;

public interface RetrieveInvoicesOutputBoundary {

    List<Map<String, Object>> retrieveInvoices() throws Exception;
    
}
