package com.trilogyed.retailapiservice.util.feign;

import com.trilogyed.retailapiservice.viewmodels.backing.InvoiceItemViewModel;
import com.trilogyed.retailapiservice.viewmodels.backing.InvoiceViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Service
@FeignClient(name = "invoice-service")
public interface InvoiceServiceClient {

    @PostMapping(value= "/invoices")
    @ResponseStatus(HttpStatus.CREATED)
    InvoiceViewModel createInvoice(@RequestBody InvoiceViewModel invoice);

    @GetMapping(value = "/invoices/{id}")
    @ResponseStatus(HttpStatus.OK)
    InvoiceViewModel getInvoice(@PathVariable("id") int invoiceId);

    @GetMapping(value = "/invoices/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    List<InvoiceViewModel> getInvoicesByCustomer(@PathVariable("id") int customerId);

    @PostMapping(value = "/items")
    @ResponseStatus(HttpStatus.CREATED)
    InvoiceItemViewModel createInvoiceItem(@RequestBody @Valid InvoiceItemViewModel item);
}
