package com.elopez.mariscal.synchronizer.modules.sender.presenter;

import com.elopez.mariscal.synchronizer.modules.sender.entity.DocumentToCancel;
import com.elopez.mariscal.synchronizer.modules.sender.service.boundary.output.SendCancelDocumentOutputBoundary;

public class SendCancelDocumentPresenter implements SendCancelDocumentOutputBoundary {

    private SendCancelDocumentGateway sendCancelDocumentGateway;
    private String contractNumber;

    public SendCancelDocumentPresenter(SendCancelDocumentGateway sendCancelDocumentGateway, String contractNumber) {
        this.sendCancelDocumentGateway = sendCancelDocumentGateway;
        this.contractNumber = contractNumber;
    }

    @Override
    public void sendCancelDocument(DocumentToCancel cancelDocument) throws Exception {
        String cancelDocumentJson = cancelDocumentToJson(cancelDocument);
        sendCancelDocumentGateway.sendCancelDocument(cancelDocumentJson);
    }

    private String cancelDocumentToJson(DocumentToCancel cancelDocument) {

        var date = cancelDocument.fecha;
        String cancelDateStr = date.getDayOfMonth() + "-" + date.getMonthValue() + "-" + date.getYear();
        String cancelDocumentJson = getDocumentJson(cancelDocument);

        StringBuilder sb = new StringBuilder()
                .append("{")
                .append("\"contrato\":\"")
                .append(contractNumber)
                .append("\",")
                .append("\"fecha\":\"")
                .append(cancelDateStr)
                .append("\",")
                .append("\"ventas\":")
                .append("[")
                .append(cancelDocumentJson)
                .append("]")
                .append("}");
        return sb.toString();
    }

    private String getDocumentJson(DocumentToCancel cancelDocument) {

        var date = cancelDocument.fecha;
        String dateStr = date.getDayOfMonth() + "-" + date.getMonthValue() + "-" + date.getYear();

        var cancelledDate = cancelDocument.anulado;
        String cancelledDateStr = cancelledDate.getDayOfMonth() + "-" + cancelledDate.getMonthValue() + "-"
                + cancelledDate.getYear();

        StringBuilder sb = new StringBuilder()
                .append("{")
                .append("\"comprobante\":\"")
                .append(cancelDocument.comprobante)
                .append("\",")
                .append("\"fecha\":\"")
                .append(dateStr)
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
                .append(cancelledDateStr)
                .append("\"");
        if (!cancelDocument.moneda.isLocal()) {
            sb.append(",")
                    .append("\"tipoCambio\":\"")
                    .append(cancelDocument.tipoCambio.doubleValue())
                    .append("\"");
        }
        sb.append("}");
        return sb.toString();
    }

}
