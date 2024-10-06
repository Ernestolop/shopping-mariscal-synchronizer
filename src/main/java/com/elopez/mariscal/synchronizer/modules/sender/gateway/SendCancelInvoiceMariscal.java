package com.elopez.mariscal.synchronizer.modules.sender.gateway;

import java.net.http.HttpResponse;

import com.elopez.mariscal.synchronizer.modules.sender.presenter.SendCancelInvoiceGateway;

public class SendCancelInvoiceMariscal implements SendCancelInvoiceGateway {

    private static final String ENDPOINT_REQUEST = "/ventas";

    @Override
    public void sendCancelInvoice(String cancelInvoice) throws Exception {

        HttpResponse<String> response = HttpClientMariscal.post(ENDPOINT_REQUEST, cancelInvoice);
        if (response.statusCode() != 200) {
            throw new Exception("Error al enviar la factura - Status Code: " + response.statusCode() + " - Mensaje: "
                    + response.body());
        }

    }

}
