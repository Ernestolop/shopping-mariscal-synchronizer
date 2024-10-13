package com.elopez.mariscal.synchronizer.modules.sender.presenter;

import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentToSend;
import com.elopez.mariscal.synchronizer.modules.sender.service.boundary.output.SendDocumentOutputBoundary;

public class SendDocumentPresenter implements SendDocumentOutputBoundary {

    private SendDocumentGateway sendDocumentGateway;
    private String contractNumber;

    public SendDocumentPresenter(SendDocumentGateway sendDocumentGateway, String contractNumber) {
        this.sendDocumentGateway = sendDocumentGateway;
        this.contractNumber = contractNumber;
    }

    @Override
    public void sendDocument(DocumentToSend document) throws Exception {
        String documentJson = body(document);
        sendDocumentGateway.sendDocument(documentJson);
    }

    private String body(DocumentToSend document) {

        var date = document.fecha;
        String dateStr = date.getDayOfMonth() + "-" + date.getMonthValue() + "-" + date.getYear();

        String documentJson = getDocumentJson(document);

        StringBuilder sb = new StringBuilder()
                .append("{")
                .append("\"contrato\":\"")
                .append(contractNumber)
                .append("\",")
                .append("\"fecha\":\"")
                .append(dateStr)
                .append("\",")
                .append("\"ventas\":")
                .append("[")
                .append(documentJson)
                .append("]")
                .append("}");
        return sb.toString();
    }

    private String getDocumentJson(DocumentToSend document) {

        var date = document.fecha;
        String dateStr = date.getDayOfMonth() + "-" + date.getMonthValue() + "-" + date.getYear();

        StringBuilder sb = new StringBuilder()
                .append("{")
                .append("\"comprobante\":\"")
                .append(document.comprobante)
                .append("\",")
                .append("\"fecha\":\"")
                .append(dateStr)
                .append("\",")
                .append("\"moneda\":\"")
                .append(document.moneda.name())
                .append("\",")
                .append("\"cliente\":\"")
                .append(document.cliente)
                .append("\",")
                .append("\"ruc\":\"")
                .append(document.ruc)
                .append("\",")
                .append("\"gravadas10\":\"")
                .append(document.gravadas10.doubleValue())
                .append("\",")
                .append("\"gravadas5\":\"")
                .append(document.gravadas5.doubleValue())
                .append("\",")
                .append("\"exentas\":\"")
                .append(document.exentas.doubleValue())
                .append("\",")
                .append("\"total\":\"")
                .append(document.total.doubleValue())
                .append("\",")
                .append("\"tipo\":\"")
                .append(document.tipo.name())
                .append("\"");
        if (!document.moneda.isLocal()) {
            sb.append(",")
                    .append("\"tipoCambio\":\"")
                    .append(document.tipoCambio.doubleValue())
                    .append("\"");
        }
        sb.append("}");
        return sb.toString();
    }

}
