package com.trilogyed.adminapiservice.controller;


import com.trilogyed.adminapiservice.service.ServiceLayer;
import com.trilogyed.adminapiservice.viewModel.InvoiceItemViewModel;
import com.trilogyed.adminapiservice.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RefreshScope
@RestController
public class InvoiceController {

    @Autowired
    ServiceLayer service;

    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    InvoiceViewModel createInvoice(@RequestBody @Valid InvoiceViewModel invoice){
        return service.saveInvoice(invoice);
    }

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<InvoiceViewModel> getAllInvoices(){
       return service.findAllInvoices();
    }

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    InvoiceViewModel getInvoice(@PathVariable("id") int invoiceId){
        return service.findInvoice(invoiceId);
    }

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.PUT)
    void updateInvoice(@PathVariable("id") int invoiceId, @RequestBody @Valid InvoiceViewModel invoice){
        service.updateInvoice(invoiceId, invoice);
    }

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.DELETE)
    void deleteInvoice(@PathVariable("id") int invoiceId){
        service.removeInvoice(invoiceId);
    }

    @RequestMapping(value = "/invoices/customer/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<InvoiceViewModel> getInvoicesByCustomer(@PathVariable("id") int customerId){
        return service.findInvoicesByCustomer(customerId);
    }



    @RequestMapping(value = "/items", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    InvoiceItemViewModel createInvoiceItem(@RequestBody @Valid InvoiceItemViewModel item){
       return service.saveInvoiceItem(item);
    }

    @RequestMapping(value = "items/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    InvoiceItemViewModel getInvoiceItem(@PathVariable("id") int itemId){
       return service.findInvoiceItem(itemId);
    }

    @RequestMapping(value = "items/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateInvoiceItem(@PathVariable("id") int itemId, @RequestBody @Valid InvoiceItemViewModel item){
        service.updateInvoiceItem(itemId, item);
    }

    @RequestMapping(value = "items/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteInvoiceItem(@PathVariable("id") int itemId){
        service.deleteInvoiceItem(itemId);
    }


}
