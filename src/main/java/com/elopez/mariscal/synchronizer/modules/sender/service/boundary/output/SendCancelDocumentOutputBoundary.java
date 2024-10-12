package com.elopez.mariscal.synchronizer.modules.sender.service.boundary.output;

import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentToCancel;

public interface SendCancelDocumentOutputBoundary {

    void sendCancelDocument(DocumentToCancel document) throws Exception;

}
