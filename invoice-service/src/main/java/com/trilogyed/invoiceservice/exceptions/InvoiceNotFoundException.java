package com.trilogyed.invoiceservice.exceptions;

public class InvoiceNotFoundException extends RuntimeException {

    public InvoiceNotFoundException(){

    }

    public InvoiceNotFoundException(String message){
        super(message);
    }
}
