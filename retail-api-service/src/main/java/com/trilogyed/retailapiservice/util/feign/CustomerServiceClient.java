package com.trilogyed.retailapiservice.util.feign;

import com.trilogyed.retailapiservice.viewmodels.backing.CustomerViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
@FeignClient(name = "customer-service")
@RequestMapping(value="/customer", produces = "application/json")
public interface CustomerServiceClient {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerViewModel getCustomer(@PathVariable("id") int id);

}
