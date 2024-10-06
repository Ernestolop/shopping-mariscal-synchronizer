package com.elopez.mariscal.synchronizer.modules.sender.presenter;

import java.time.LocalDate;

import com.elopez.mariscal.synchronizer.modules.sender.entity.InvoiceToSend;
import com.elopez.mariscal.synchronizer.modules.sender.service.boundary.output.SendInvoiceOutputBoundary;

public class SendInvoicePresenter implements SendInvoiceOutputBoundary {

    private SendInvoiceGateway sendInvoiceGateway;

    public SendInvoicePresenter(SendInvoiceGateway sendInvoiceGateway) {
        this.sendInvoiceGateway = sendInvoiceGateway;
    }

    @Override
    public void sendInvoice(InvoiceToSend invoice) throws Exception {
        String invoiceJson = body(invoice);
        sendInvoiceGateway.sendInvoice(invoiceJson);
    }

    private String body(InvoiceToSend invoice) {

        LocalDate currentDate = LocalDate.now();
        String CurrenDateStr = currentDate.getDayOfMonth() + "-" + currentDate.getMonthValue() + "-"
                + currentDate.getYear();
        String invoiceJson = getInvoiceJson(invoice);

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
                .append(invoiceJson)
                .append("]");
        return sb.toString();
    }

    private String getInvoiceJson(InvoiceToSend invoice) {
        StringBuilder sb = new StringBuilder()
                .append("{")
                .append("\"comprobante\":\"")
                .append(invoice.comprobante)
                .append("\",")
                .append("\"fecha\":\"")
                .append(invoice.fecha.toString())
                .append("\",")
                .append("\"moneda\":\"")
                .append(invoice.moneda.name())
                .append("\",")
                .append("\"cliente\":\"")
                .append(invoice.cliente)
                .append("\",")
                .append("\"ruc\":\"")
                .append(invoice.ruc)
                .append("\",")
                .append("\"gravadas10\":\"")
                .append(invoice.gravadas10.doubleValue())
                .append("\",")
                .append("\"gravadas5\":\"")
                .append(invoice.gravadas5.doubleValue())
                .append("\",")
                .append("\"exentas\":\"")
                .append(invoice.exentas.doubleValue())
                .append("\",")
                .append("\"total\":\"")
                .append(invoice.total.doubleValue())
                .append("\",")
                .append("\"tipo\":\"")
                .append(invoice.tipo.name());
        if (!invoice.moneda.isLocal()) {
            sb.append("\",")
                    .append("\"tipoCambio\":\"")
                    .append(invoice.tipoCambio.doubleValue())
                    .append("\"");
        }
        sb.append("}");
        return sb.toString();
    }

}
