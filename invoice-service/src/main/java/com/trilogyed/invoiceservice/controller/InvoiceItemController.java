package com.trilogyed.invoiceservice.controller;

import com.trilogyed.invoiceservice.service.ServiceLayer;
import com.trilogyed.invoiceservice.viewmodels.InvoiceItemViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value = "/items")
public class InvoiceItemController {

    @Autowired
    ServiceLayer sl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceItemViewModel createInvoiceItem(@RequestBody @Valid InvoiceItemViewModel item) {
        return sl.createInvoiceItem(item);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InvoiceItemViewModel getInvoiceItem(@PathVariable("id") int itemId) {
        InvoiceItemViewModel item = sl.getInvoiceItem(itemId);

        return item;
    }

    @GetMapping(value = "/invoice/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceItemViewModel> getInvoiceItemsByInvoice(@PathVariable("id") int invoiceId) {
        return sl.getInvoiceItemsByInvoice(invoiceId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceItemViewModel> getAllInvoiceItems(){
        return sl.getAllInvoiceItems();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoiceItem(@PathVariable("id") int itemId, @RequestBody @Valid InvoiceItemViewModel item) {
        sl.updateInvoiceItem(item);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoiceItem(@PathVariable("id") int itemId) {
        sl.deleteInvoiceItem(itemId);
    }
}
