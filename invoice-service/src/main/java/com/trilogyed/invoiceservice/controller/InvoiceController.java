package com.trilogyed.invoiceservice.controller;

import com.trilogyed.invoiceservice.service.ServiceLayer;
import com.trilogyed.invoiceservice.viewmodels.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value = "/invoices")
public class InvoiceController {

    @Autowired
    ServiceLayer sl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceViewModel createInvoice(@RequestBody @Valid InvoiceViewModel invoice) {
        return sl.createInvoice(invoice);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InvoiceViewModel getInvoice(@PathVariable("id") int invoiceId) {
        InvoiceViewModel invoice = sl.getInvoice(invoiceId);

        return invoice;
    }

    @GetMapping(value = "/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getInvoicesByCustomer(@PathVariable("id") int customerId) {
        return sl.getInvoicesByCustomer(customerId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getAllInvoices(){
        return sl.getAllInvoices();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoice(@PathVariable("id") int invoiceId, @RequestBody @Valid InvoiceViewModel invoice) {
        sl.updateInvoice(invoice);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable("id") int invoiceId) {
        sl.deleteInvoice(invoiceId);
    }

//    @DeleteMapping(value = "/customer/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteByCustomer(@PathVariable("id") int customerId){
//        sl.deleteInvoicesByCustomer(customerId);
//    }
}
