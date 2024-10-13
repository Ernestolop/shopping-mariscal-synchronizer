package com.elopez.mariscal.synchronizer.modules.retriever.gateway;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetrieveCreditNotesJpa extends RetrieveDocumentsJpa {

    @Autowired
    private CreditNoteRepository creditNoteRepository;

    @Override
    protected List<Object[]> findDocuments(String initialDocumentNumber) {
        return creditNoteRepository.findNewCreditNotes(initialDocumentNumber);
    }
}