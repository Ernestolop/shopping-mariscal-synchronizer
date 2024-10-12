package com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.input;

import java.util.List;
import java.util.Map;

public interface RetrieveInvoicesInputBoundary {
    
    List<Map<String, Object>> retrieveInvoices() throws Exception;

}
