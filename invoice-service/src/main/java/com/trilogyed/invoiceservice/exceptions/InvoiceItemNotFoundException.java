package com.trilogyed.invoiceservice.exceptions;

public class InvoiceItemNotFoundException  extends RuntimeException {

    public InvoiceItemNotFoundException(){

    }

    public InvoiceItemNotFoundException(String message){
        super(message);
    }
}
