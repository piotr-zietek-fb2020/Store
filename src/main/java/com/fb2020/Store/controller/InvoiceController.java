package com.fb2020.Store.controller;

import com.fb2020.Store.entity.Invoice;
import com.fb2020.Store.exception.invoice.InvoiceException;
import com.fb2020.Store.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;

    @GetMapping("/invoices")
    public List<Invoice> getAll() {
        return invoiceService.getAll();
    }

    @GetMapping("/invoices/{id}")
    public Optional<Invoice> getInvoiceById(@PathVariable("id") Long id) {
        try {
            return invoiceService.getInvoiceById(id);
        } catch (InvoiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
