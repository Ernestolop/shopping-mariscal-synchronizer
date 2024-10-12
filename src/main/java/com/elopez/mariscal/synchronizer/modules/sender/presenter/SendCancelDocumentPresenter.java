package com.elopez.mariscal.synchronizer.modules.sender.presenter;

import java.time.LocalDate;

import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentToCancel;
import com.elopez.mariscal.synchronizer.modules.sender.service.boundary.output.SendCancelDocumentOutputBoundary;

public class SendCancelDocumentPresenter implements SendCancelDocumentOutputBoundary {

    private SendCancelDocumentGateway sendCancelDocumentGateway;

    public SendCancelDocumentPresenter(SendCancelDocumentGateway sendCancelDocumentGateway) {
        this.sendCancelDocumentGateway = sendCancelDocumentGateway;
    }

    @Override
    public void sendCancelDocument(DocumentToCancel cancelDocument) throws Exception {
        String cancelDocumentJson = cancelDocumentToJson(cancelDocument);
        sendCancelDocumentGateway.sendCancelDocument(cancelDocumentJson);
    }

    private String cancelDocumentToJson(DocumentToCancel cancelDocument) {

        LocalDate currentDate = LocalDate.now();
        String CurrenDateStr = currentDate.getDayOfMonth() + "-" + currentDate.getMonthValue() + "-"
                + currentDate.getYear();
        String cancelDocumentJson = getDocumentJson(cancelDocument);

        StringBuilder sb = new StringBuilder()
                .append("{")
                .append("\"contrato\":\"")
                .append("C0000001218")
                .append("\",")
                .append("\"fecha\":\"")
                .append(CurrenDateStr)
                .append("\",")
                .append("\"ventas\":")
                .append("[")
                .append(cancelDocumentJson)
                .append("]");
        return sb.toString();
    }

    private String getDocumentJson(DocumentToCancel cancelDocument) {

        StringBuilder sb = new StringBuilder()
                .append("{")
                .append("\"comprobante\":\"")
                .append(cancelDocument.comprobante)
                .append("\",")
                .append("\"fecha\":\"")
                .append(cancelDocument.fecha.toString())
                .append("\",")
                .append("\"moneda\":\"")
                .append(cancelDocument.moneda.name())
                .append("\",")
                .append("\"cliente\":\"")
                .append(cancelDocument.cliente)
                .append("\",")
                .append("\"ruc\":\"")
                .append(cancelDocument.ruc)
                .append("\",")
                .append("\"gravadas10\":\"")
                .append(cancelDocument.gravadas10.doubleValue())
                .append("\",")
                .append("\"gravadas5\":\"")
                .append(cancelDocument.gravadas5.doubleValue())
                .append("\",")
                .append("\"exentas\":\"")
                .append(cancelDocument.exentas.doubleValue())
                .append("\",")
                .append("\"total\":\"")
                .append(cancelDocument.total.doubleValue())
                .append("\",")
                .append("\"tipo\":\"")
                .append(cancelDocument.tipo.name())
                .append("\",")
                .append("\"anulado\":\"")
                .append(cancelDocument.anulado);
        if (!cancelDocument.moneda.isLocal()) {
            sb.append("\",")
                    .append("\"tipoCambio\":\"")
                    .append(cancelDocument.tipoCambio.doubleValue())
                    .append("\"");
        }
        sb.append("}");
        return sb.toString();
    }

}
