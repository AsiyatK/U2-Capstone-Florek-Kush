package com.trilogyed.adminapiservice.util.feign;


import com.trilogyed.adminapiservice.viewModel.InvoiceItemViewModel;
import com.trilogyed.adminapiservice.viewModel.InvoiceViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "invoice-service")
public interface InvoiceClient {

    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    InvoiceViewModel createInvoice(@RequestBody @Valid InvoiceViewModel invoice);

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    List<InvoiceViewModel> getAllInvoices();

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    InvoiceViewModel getInvoice(@PathVariable("id") int invoiceId);

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.PUT)
    void updateInvoice(@PathVariable("id") int invoiceId, @RequestBody @Valid InvoiceViewModel invoice);

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.DELETE)
    void deleteInvoice(@PathVariable("id") int invoiceId);

    @RequestMapping(value = "/invoices/customer/{id}", method = RequestMethod.GET)
    List<InvoiceViewModel> getInvoicesByCustomer(@PathVariable("id") int customerId);



    @RequestMapping(value = "/items", method = RequestMethod.POST)
    InvoiceItemViewModel createInvoiceItem(@RequestBody @Valid InvoiceItemViewModel item);

    @RequestMapping(value = "items/{id}", method = RequestMethod.GET)
    InvoiceItemViewModel getInvoiceItem(@PathVariable("id") int itemId);

    @RequestMapping(value = "items/{id}", method = RequestMethod.PUT)
    void updateInvoiceItem(@PathVariable("id") int itemId, @RequestBody @Valid InvoiceItemViewModel item);

    @RequestMapping(value = "items/{id}", method = RequestMethod.DELETE)
    void deleteInvoiceItem(@PathVariable("id") int itemId);

}

