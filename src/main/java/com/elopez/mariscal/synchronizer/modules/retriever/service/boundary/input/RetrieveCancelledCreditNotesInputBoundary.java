package com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.input;

import java.util.List;
import java.util.Map;

public interface RetrieveCancelledCreditNotesInputBoundary {

    List<Map<String, Object>> retrieveCancelledCreditNotes() throws Exception;
    
}
