package com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.output;

import java.util.List;
import java.util.Map;

public interface RetrieveCreditNotesOutputBoundary {

    List<Map<String, Object>> retrieveCreditNotes() throws Exception;

}
