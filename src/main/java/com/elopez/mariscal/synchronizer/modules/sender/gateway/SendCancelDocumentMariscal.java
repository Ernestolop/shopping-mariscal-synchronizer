package com.elopez.mariscal.synchronizer.modules.sender.gateway;

import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.elopez.mariscal.synchronizer.modules.sender.presenter.SendCancelDocumentGateway;

public class SendCancelDocumentMariscal implements SendCancelDocumentGateway {

    Logger logger = LoggerFactory.getLogger(SendCancelDocumentMariscal.class);

    private static final String ENDPOINT_REQUEST = "/ventas";

    @Value("${mariscal.token}")
    private String token;

    @Override
    public void sendCancelDocument(String cancelDocument) throws Exception {
        logger.info("Enviando anulacion de documento a Mariscal");
        logger.debug("Anulacion de documento: " + cancelDocument);
        HttpResponse<String> response = HttpClientMariscal.post(ENDPOINT_REQUEST, cancelDocument, token);
        if (response.statusCode() != 200) {
            throw new Exception(
                    "Error al enviar la anulacion de documento - Status Code: " + response.statusCode() + " - Mensaje: "
                            + response.body());
        }
        logger.info("Anulacion de documento enviada exitosamente");
    }

}
