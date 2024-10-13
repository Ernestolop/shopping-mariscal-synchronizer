package com.elopez.mariscal.synchronizer.modules.retriever.gateway;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetrieveCancelledCreditNotesJpa extends RetrieveCancelledDocumentsJpa {

    @Autowired
    private CreditNoteRepository creditNoteRepository;

    @Override
    protected List<Object[]> findCancelledDocuments(String initialDocumentNumber) {
        return creditNoteRepository.findCancelledCreditNotes(initialDocumentNumber);
    }
}