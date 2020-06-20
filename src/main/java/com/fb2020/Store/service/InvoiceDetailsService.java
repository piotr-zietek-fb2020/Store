package com.fb2020.Store.service;

import com.fb2020.Store.entity.InvoiceDetails;
import com.fb2020.Store.repository.InvoiceDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceDetailsService {
    @Autowired
    InvoiceDetailsRepository invoiceDetailsRepository;

    public void createInvoiceDetails(InvoiceDetails invoiceDetails) {
        invoiceDetailsRepository.save(invoiceDetails);
    }

    public void deleteAll() {
        invoiceDetailsRepository.deleteAll();
    }
}
