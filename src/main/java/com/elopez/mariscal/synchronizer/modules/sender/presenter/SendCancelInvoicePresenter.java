package com.elopez.mariscal.synchronizer.modules.sender.presenter;

import java.time.LocalDate;

import com.elopez.mariscal.synchronizer.modules.sender.entity.InvoiceToCancel;
import com.elopez.mariscal.synchronizer.modules.sender.service.boundary.output.SendCancelInvoiceOutputBoundary;

public class SendCancelInvoicePresenter implements SendCancelInvoiceOutputBoundary {

    private SendCancelInvoiceGateway sendCancelInvoiceGateway;

    public SendCancelInvoicePresenter(SendCancelInvoiceGateway sendCancelInvoiceGateway) {
        this.sendCancelInvoiceGateway = sendCancelInvoiceGateway;
    }

    @Override
    public void sendCancelInvoice(InvoiceToCancel cancelInvoice) throws Exception {
        String cancelInvoiceJson = cancelInvoiceToJson(cancelInvoice);
        sendCancelInvoiceGateway.sendCancelInvoice(cancelInvoiceJson);
    }

    private String cancelInvoiceToJson(InvoiceToCancel cancelInvoice) {

        LocalDate currentDate = LocalDate.now();
        String CurrenDateStr = currentDate.getDayOfMonth() + "-" + currentDate.getMonthValue() + "-"
                + currentDate.getYear();
        String cancelInvoiceJson = getInvoiceJson(cancelInvoice);

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
                .append(cancelInvoiceJson)
                .append("]");
        return sb.toString();
    }

    private String getInvoiceJson(InvoiceToCancel cancelInvoice) {

        StringBuilder sb = new StringBuilder()
                .append("{")
                .append("\"comprobante\":\"")
                .append(cancelInvoice.comprobante)
                .append("\",")
                .append("\"fecha\":\"")
                .append(cancelInvoice.fecha.toString())
                .append("\",")
                .append("\"moneda\":\"")
                .append(cancelInvoice.moneda.name())
                .append("\",")
                .append("\"cliente\":\"")
                .append(cancelInvoice.cliente)
                .append("\",")
                .append("\"ruc\":\"")
                .append(cancelInvoice.ruc)
                .append("\",")
                .append("\"gravadas10\":\"")
                .append(cancelInvoice.gravadas10.doubleValue())
                .append("\",")
                .append("\"gravadas5\":\"")
                .append(cancelInvoice.gravadas5.doubleValue())
                .append("\",")
                .append("\"exentas\":\"")
                .append(cancelInvoice.exentas.doubleValue())
                .append("\",")
                .append("\"total\":\"")
                .append(cancelInvoice.total.doubleValue())
                .append("\",")
                .append("\"tipo\":\"")
                .append(cancelInvoice.tipo.name())
                .append("\",")
                .append("\"anulado\":\"")
                .append(cancelInvoice.anulado);
        if (!cancelInvoice.moneda.isLocal()) {
            sb.append("\",")
                    .append("\"tipoCambio\":\"")
                    .append(cancelInvoice.tipoCambio.doubleValue())
                    .append("\"");
        }
        sb.append("}");
        return sb.toString();
    }

}
