package com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.input;

import java.util.List;
import java.util.Map;

public interface RetrieveCancelledInvoicesInputBoundary {
    
    List<Map<String, Object>> retrieveCancelledInvoices() throws Exception;

}
