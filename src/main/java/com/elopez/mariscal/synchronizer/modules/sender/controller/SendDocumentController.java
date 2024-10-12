package com.elopez.mariscal.synchronizer.modules.sender.controller;

import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentToSend;
import com.elopez.mariscal.synchronizer.modules.sender.service.boundary.input.SendDocumentInputBoundary;

public class SendDocumentController {

    private SendDocumentInputBoundary sendDocumentInputBoundary;

    public SendDocumentController(SendDocumentInputBoundary sendDocumentInputBoundary) {
        this.sendDocumentInputBoundary = sendDocumentInputBoundary;
    }

    public void sendDocument(DocumentToSend document) throws Exception {
        sendDocumentInputBoundary.sendDocument(document);
    }

}
