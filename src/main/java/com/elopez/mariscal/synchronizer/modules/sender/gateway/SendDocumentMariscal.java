package com.elopez.mariscal.synchronizer.modules.sender.gateway;

import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elopez.mariscal.synchronizer.modules.sender.presenter.SendDocumentGateway;

public class SendDocumentMariscal implements SendDocumentGateway {

    Logger logger = LoggerFactory.getLogger(SendDocumentMariscal.class);

    private static final String ENDPOINT_REQUEST = "/ventas";

    private String token;

    public SendDocumentMariscal(String token) {
        this.token = token;
    }

    @Override
    public void sendDocument(String document) throws Exception {

        logger.info("Enviando documento a Mariscal");
        logger.info("Documento: " + document);
        HttpResponse<String> response = HttpClientMariscal.post(ENDPOINT_REQUEST, document, token);
        if (response.statusCode() != 200) {
            logger.error("Error al enviar el documento - Status Code: " + response.statusCode() + " - Mensaje: "
                    + response.body());
            throw new Exception("Error al enviar el documento - Status Code: " + response.statusCode() + " - Mensaje: "
                    + response.body());
        }
        logger.info("Documento enviado exitosamente");
    }

}
