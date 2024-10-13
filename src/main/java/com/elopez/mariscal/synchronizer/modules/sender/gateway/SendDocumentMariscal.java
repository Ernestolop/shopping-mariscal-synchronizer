package com.elopez.mariscal.synchronizer.modules.sender.gateway;

import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elopez.mariscal.synchronizer.modules.sender.presenter.SendDocumentGateway;

public class SendDocumentMariscal implements SendDocumentGateway {

    Logger logger = LoggerFactory.getLogger(SendDocumentMariscal.class);

    private static final String ENDPOINT_REQUEST = "/ventas";

    @Override
    public void sendDocument(String document) throws Exception {

        logger.info("Enviando documento a Mariscal");
        logger.debug("Documento: " + document);
        HttpResponse<String> response = HttpClientMariscal.post(ENDPOINT_REQUEST, document);
        if (response.statusCode() != 200) {
            throw new Exception("Error al enviar el documento - Status Code: " + response.statusCode() + " - Mensaje: "
                    + response.body());
        } else {
            logger.info("Documento enviado exitosamente");
        }

    }

}
