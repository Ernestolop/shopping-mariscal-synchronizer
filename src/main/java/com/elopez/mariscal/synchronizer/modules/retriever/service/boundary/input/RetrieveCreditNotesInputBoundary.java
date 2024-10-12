package com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.input;

import java.util.List;
import java.util.Map;

public interface RetrieveCreditNotesInputBoundary {

    List<Map<String, Object>> retrieveCreditNotes() throws Exception;

}
