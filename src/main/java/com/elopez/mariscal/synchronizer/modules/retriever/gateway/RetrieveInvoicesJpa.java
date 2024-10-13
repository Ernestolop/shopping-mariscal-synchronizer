package com.elopez.mariscal.synchronizer.modules.retriever.gateway;

import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class RetrieveInvoicesJpa extends RetrieveDocumentsJpa {

    private InvoiceRepository invoiceRepository;

    @Override
    protected List<Object[]> findDocuments(String initialDocumentNumber) {
        return invoiceRepository.findInvoices(initialDocumentNumber);
    }

}
