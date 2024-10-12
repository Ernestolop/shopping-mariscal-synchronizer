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

import com.elopez.mariscal.synchronizer.modules.retriever.service.boundary.output.RetrieverOutputBoundary;

@Service
public class RetrieveCreditNotesJpa implements RetrieverOutputBoundary {

    Logger logger = LoggerFactory.getLogger(RetrieveCreditNotesJpa.class);

    @Autowired
    private CreditNoteRepository creditNoteRepository;

    @Value("${retriever.initial.invoice.number}")
    private String initialCreditNoteNumber;

    @Override
    public List<Map<String, Object>> retrieve() throws Exception {
        var creditNotes = creditNoteRepository.findNewCreditNotes(initialCreditNoteNumber);
        var mapCreditNotes = convertToMap(creditNotes);
        return mapCreditNotes;
    }

    private List<Map<String, Object>> convertToMap(List<Object[]> creditNotes) {
        var result = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < creditNotes.size(); i++) {
            var creditNote = creditNotes.get(i);
            var creditNoteMap = new HashMap<String, Object>();
            try {
                creditNoteMap.put("id", creditNote[0]);
                creditNoteMap.put("fecha", dateToLocalDate((Date) creditNote[1]));
                creditNoteMap.put("moneda", creditNote[2]);
                creditNoteMap.put("tipoCambio", (BigDecimal) creditNote[3]);
                creditNoteMap.put("cliente", creditNote[4]);
                creditNoteMap.put("ruc", creditNote[5]);
                creditNoteMap.put("gravadas10", (BigDecimal) creditNote[6]);
                creditNoteMap.put("gravadas10Extranjero", (BigDecimal) creditNote[7]);
                creditNoteMap.put("gravadas5", BigDecimal.ZERO);
                creditNoteMap.put("gravadas5Extranjero", BigDecimal.ZERO);
                creditNoteMap.put("exentas", BigDecimal.ZERO);
                creditNoteMap.put("exentasExtranjero", BigDecimal.ZERO);
                creditNoteMap.put("total", (BigDecimal) creditNote[6]);
                creditNoteMap.put("totalExtranjero", (BigDecimal) creditNote[7]);
                creditNoteMap.put("comprobante", creditNoteNumber((String) creditNote[15]));
                result.add(creditNoteMap);
            } catch (Exception e) {
                logger.error(
                        "No se pudo procesar la nota de credito en la iteracion " + i + " con el id " + creditNote[0]);
                logger.error("Motivo: " + e.getMessage());
            }
        }
        return result;
    }

    private LocalDate dateToLocalDate(Date docDate) {
        return docDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private String creditNoteNumber(String uExxFEClaAcc) {
        int index = uExxFEClaAcc.indexOf(initialCreditNoteNumber);
        return uExxFEClaAcc.substring(index);
    }

}
