package com.elopez.mariscal.synchronizer.modules.sender.controller;

import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentToCancel;
import com.elopez.mariscal.synchronizer.modules.sender.service.boundary.input.SendCancelDocumentInputBoundary;

public class SendCancelDocument {

    private SendCancelDocumentInputBoundary sendCancelDocumentInputBoundary;

    public SendCancelDocument(SendCancelDocumentInputBoundary sendCancelDocumentInteractor) {
        this.sendCancelDocumentInputBoundary = sendCancelDocumentInteractor;
    }

    public void sendCancelDocument(DocumentToCancel document) throws Exception {
        sendCancelDocumentInputBoundary.cancelDocument(document);
    }

}
