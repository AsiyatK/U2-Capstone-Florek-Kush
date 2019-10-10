package com.trilogyed.invoiceservice.service;

import com.trilogyed.invoiceservice.dao.InvoiceDao;
import com.trilogyed.invoiceservice.dao.InvoiceItemDao;
import com.trilogyed.invoiceservice.viewmodels.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceLayer {

    private InvoiceDao invoiceDao;
    private InvoiceItemDao itemDao;

    @Autowired
    public ServiceLayer(InvoiceDao invoiceDao, InvoiceItemDao itemDao){
        this.invoiceDao=invoiceDao;
        this.itemDao=itemDao;
    }

    public InvoiceViewModel createInvoice(InvoiceViewModel ivm){

        return ivm;
    }

}
