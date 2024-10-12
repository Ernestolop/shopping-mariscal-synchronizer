package com.elopez.mariscal.synchronizer.modules.sender.service.boundary.input;

import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentToSend;

public interface SendDocumentInputBoundary {

    void sendDocument(DocumentToSend document) throws Exception;

}
