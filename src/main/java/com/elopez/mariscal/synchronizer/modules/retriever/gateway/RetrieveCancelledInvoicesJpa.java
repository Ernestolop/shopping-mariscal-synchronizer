package com.elopez.mariscal.synchronizer.modules.retriever.gateway;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetrieveCancelledInvoicesJpa extends RetrieveCancelledDocumentsJpa {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    protected List<Object[]> findCancelledDocuments(String initialDocumentNumber) {
        return invoiceRepository.findCancelledInvoices(initialDocumentNumber);
    }
}