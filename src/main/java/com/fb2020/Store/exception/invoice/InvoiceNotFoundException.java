package com.fb2020.Store.exception.invoice;

public class InvoiceNotFoundException extends InvoiceException{
    public InvoiceNotFoundException(Long id) {
        super("No invoice in database with id: " + id);
    }
}
