package com.elopez.mariscal.synchronizer.modules.sender.gateway;

import java.net.http.HttpResponse;

import com.elopez.mariscal.synchronizer.modules.sender.presenter.SendDocumentGateway;

public class SendDocumentMariscal implements SendDocumentGateway {

    private static final String ENDPOINT_REQUEST = "/ventas";

    @Override
    public void sendDocument(String document) throws Exception {

        System.out.println("Enviando documento a Mariscal");
        System.out.println(document);
        HttpResponse<String> response = HttpClientMariscal.post(ENDPOINT_REQUEST, document);
        if (response.statusCode() != 200) {
            throw new Exception("Error al enviar el documento - Status Code: " + response.statusCode() + " - Mensaje: "
                    + response.body());
        } else {
            System.out.println("Documento enviado correctamente" + response.body());
        }

    }

}
