package com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.output;

import java.util.List;
import java.util.Map;

public interface RetrieverOutputBoundary {

    List<Map<String, Object>> retrieve() throws Exception;
    
}
