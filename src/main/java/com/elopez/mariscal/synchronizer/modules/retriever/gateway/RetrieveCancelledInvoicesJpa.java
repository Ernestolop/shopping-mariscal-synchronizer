package com.elopez.mariscal.synchronizer.modules.retriever.gateway;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.output.RetrieveCancelledInvoicesOutputBoundary;

@Service
public class RetrieveCancelledInvoicesJpa implements RetrieveCancelledInvoicesOutputBoundary {

    Logger logger = LoggerFactory.getLogger(RetrieveInvoicesJpa.class);

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Value("${ndm.initial.invoice.number}")
    private String initialInvoiceNumber;

    @Override
    public List<Map<String, Object>> retrieveCancelledInvoices() throws Exception {
        var invoices = invoiceRepository.findCancelledInvoices(initialInvoiceNumber);
        var mapInvoices = convertToMap(invoices);
        return mapInvoices;
    }

    private List<Map<String, Object>> convertToMap(List<Object[]> invoices) {
        var result = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < invoices.size(); i++) {
            var invoice = invoices.get(i);
            var invoiceMap = new HashMap<String, Object>();
            try {
                invoiceMap.put("id", invoice[0]);
                invoiceMap.put("fecha", dateToLocalDate((Date) invoice[1]));
                invoiceMap.put("moneda", invoice[2]);
                invoiceMap.put("tipoCambio", (BigDecimal) invoice[3]);
                invoiceMap.put("cliente", invoice[4]);
                invoiceMap.put("ruc", invoice[5]);
                invoiceMap.put("gravadas10", (BigDecimal) invoice[6]);
                invoiceMap.put("gravadas10Extranjero", (BigDecimal) invoice[7]);
                invoiceMap.put("gravadas5", BigDecimal.ZERO);
                invoiceMap.put("gravadas5Extranjero", BigDecimal.ZERO);
                invoiceMap.put("exentas", BigDecimal.ZERO);
                invoiceMap.put("exentasExtranjero", BigDecimal.ZERO);
                invoiceMap.put("total", (BigDecimal) invoice[6]);
                invoiceMap.put("totalExtranjero", (BigDecimal) invoice[7]);
                invoiceMap.put("comprobante", invoiceNumber((String) invoice[15]));
                invoiceMap.put("anulado", dateToLocalDate((Date) invoice[16]));
                result.add(invoiceMap);
            } catch (Exception e) {
                logger.error("No se pudo procesar la factura anulada en la iteracion " + i + " con el id " + invoice[0]);
                logger.error("Motivo: " + e.getMessage());
            }
        }
        return result;
    }

    private LocalDate dateToLocalDate(Date docDate) {
        return docDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private String invoiceNumber(String uExxFEClaAcc) {
        int index = uExxFEClaAcc.indexOf(initialInvoiceNumber);
        return uExxFEClaAcc.substring(index);
    }

}
