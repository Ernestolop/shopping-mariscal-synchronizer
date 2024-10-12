package com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.output;

import java.util.List;
import java.util.Map;

public interface RetrieveCancelledInvoicesOutputBoundary {

    List<Map<String, Object>> retrieveCancelledInvoices() throws Exception;

    
}
