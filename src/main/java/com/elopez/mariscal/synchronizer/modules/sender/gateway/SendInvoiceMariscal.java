package com.elopez.mariscal.synchronizer.modules.sender.gateway;

import java.net.http.HttpResponse;

import com.elopez.mariscal.synchronizer.modules.sender.presenter.SendInvoiceGateway;

public class SendInvoiceMariscal implements SendInvoiceGateway {

    private static final String ENDPOINT_REQUEST = "/ventas";

    @Override
    public void sendInvoice(String invoice) throws Exception {

        HttpResponse<String> response = HttpClientMariscal.post(ENDPOINT_REQUEST, invoice);
        if (response.statusCode() != 200) {
            throw new Exception("Error al enviar la factura - Status Code: " + response.statusCode() + " - Mensaje: "
                    + response.body());
        }

    }

}
