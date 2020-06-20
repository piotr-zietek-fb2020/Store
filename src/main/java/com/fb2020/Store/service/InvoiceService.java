package com.fb2020.Store.service;

import com.fb2020.Store.entity.Invoice;
import com.fb2020.Store.exception.invoice.InvoiceException;
import com.fb2020.Store.exception.invoice.InvoiceNotFoundException;
import com.fb2020.Store.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<Invoice> getAll() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> getInvoiceById(Long id) throws InvoiceException {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if(!invoice.isPresent()) {
            throw new InvoiceNotFoundException(id);
        }
        return invoice;
    }
}
