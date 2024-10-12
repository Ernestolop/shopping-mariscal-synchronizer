package com.elopez.mariscal.synchronizer.modules.sender.service.boundary.input;

import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentToCancel;

public interface SendCancelDocumentInputBoundary {
    
    void cancelDocument(DocumentToCancel document) throws Exception;

}
