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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.output.RetrieverOutputBoundary;

@Service
public abstract class RetrieveDocumentsJpa implements RetrieverOutputBoundary {

    Logger logger = LoggerFactory.getLogger(RetrieveDocumentsJpa.class);

    @Value("${retriever.initial.document.number}")
    protected String initialDocumentNumber;

    protected abstract List<Object[]> findDocuments(String initialDocumentNumber);

    @Override
    public List<Map<String, Object>> retrieve() throws Exception {
        var documents = findDocuments(initialDocumentNumber);
        var mapDocuments = convertToMap(documents);
        return mapDocuments;
    }

    private List<Map<String, Object>> convertToMap(List<Object[]> documents) {
        var result = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < documents.size(); i++) {
            var document = documents.get(i);
            var documentMap = new HashMap<String, Object>();
            try {
                documentMap.put("id", document[0]);
                documentMap.put("fecha", dateToLocalDate((Date) document[1]));
                documentMap.put("moneda", document[2]);
                documentMap.put("tipoCambio", (BigDecimal) document[3]);
                documentMap.put("cliente", document[4]);
                documentMap.put("ruc", document[5]);
                documentMap.put("gravadas10", (BigDecimal) document[6]);
                documentMap.put("gravadas10Extranjero", (BigDecimal) document[7]);
                documentMap.put("gravadas5", BigDecimal.ZERO);
                documentMap.put("gravadas5Extranjero", BigDecimal.ZERO);
                documentMap.put("exentas", BigDecimal.ZERO);
                documentMap.put("exentasExtranjero", BigDecimal.ZERO);
                documentMap.put("total", (BigDecimal) document[6]);
                documentMap.put("totalExtranjero", (BigDecimal) document[7]);
                documentMap.put("comprobante", documentNumber((String) document[15]));
                result.add(documentMap);
            } catch (Exception e) {
                logger.error("No se pudo procesar el documento en la iteracion " + i + " con el id " + document[0]);
                logger.error("Motivo: " + e.getMessage());
            }
        }
        return result;
    }

    private LocalDate dateToLocalDate(Date docDate) {
        return docDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private String documentNumber(String uExxFEClaAcc) {
        int index = uExxFEClaAcc.indexOf(initialDocumentNumber);
        return uExxFEClaAcc.substring(index);
    }
}