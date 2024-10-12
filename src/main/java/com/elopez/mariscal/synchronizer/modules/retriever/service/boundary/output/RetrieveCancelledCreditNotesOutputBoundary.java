package com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.output;

import java.util.List;
import java.util.Map;

public interface RetrieveCancelledCreditNotesOutputBoundary {

    List<Map<String, Object>> retrieveCancelledCreditNotes() throws Exception;

}
