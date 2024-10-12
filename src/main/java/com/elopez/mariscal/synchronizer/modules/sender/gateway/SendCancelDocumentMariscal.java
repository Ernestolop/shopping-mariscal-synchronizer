package com.elopez.mariscal.synchronizer.modules.sender.gateway;

import java.net.http.HttpResponse;

import com.elopez.mariscal.synchronizer.modules.sender.presenter.SendCancelDocumentGateway;

public class SendCancelDocumentMariscal implements SendCancelDocumentGateway {

    private static final String ENDPOINT_REQUEST = "/ventas";

    @Override
    public void sendCancelDocument(String cancelDocument) throws Exception {

        HttpResponse<String> response = HttpClientMariscal.post(ENDPOINT_REQUEST, cancelDocument);
        if (response.statusCode() != 200) {
            throw new Exception("Error al enviar la anulacion de documento - Status Code: " + response.statusCode() + " - Mensaje: "
                    + response.body());
        }

    }

}
