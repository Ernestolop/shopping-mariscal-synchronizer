package com.elopez.mariscal.synchronizer.modules.sender.service.boundary.output;

import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentToSend;

public interface SendDocumentOutputBoundary {

    void sendDocument(DocumentToSend Document) throws Exception;

}
