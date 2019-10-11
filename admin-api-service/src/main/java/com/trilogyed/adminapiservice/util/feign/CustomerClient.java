package com.trilogyed.adminapiservice.util.feign;

import com.trilogyed.adminapiservice.model.Customer;
import com.trilogyed.adminapiservice.viewModel.CustomerViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "customer-service")
public interface CustomerClient {
    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    CustomerViewModel createCustomer(@RequestBody @Valid Customer customer);

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<CustomerViewModel> getAllCustomers();

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    CustomerViewModel getCustomer(@PathVariable("id") int id);


    @RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateCustomer(@PathVariable("id") int id, @RequestBody @Valid Customer customer);

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCustomer(@PathVariable("id") int id);
}
