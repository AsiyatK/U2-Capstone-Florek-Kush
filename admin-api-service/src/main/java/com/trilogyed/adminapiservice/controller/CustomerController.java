package com.trilogyed.adminapiservice.controller;

import com.trilogyed.adminapiservice.exception.NotFoundException;
import com.trilogyed.adminapiservice.service.ServiceLayer;
import com.trilogyed.adminapiservice.viewModel.CustomerViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RefreshScope
@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    ServiceLayer service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerViewModel saveCustomer(@RequestBody @Valid CustomerViewModel cvm){
        return service.saveCustomer(cvm);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerViewModel> findAllCustomers(){
        return service.findAllCustomers();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerViewModel findCustomer(@PathVariable("id") int customerId){
        CustomerViewModel cvm = service.findCustomer(customerId);
        if(cvm == null){
            throw new NotFoundException("Customer with id " + customerId + " not found");
        }
        return cvm;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@PathVariable("id") int customerId, @RequestBody @Valid CustomerViewModel cvm){
        if(cvm.getCustomerId() == 0){
            cvm.setCustomerId(customerId);
        }
        if(customerId != cvm.getCustomerId()){
            throw new IllegalArgumentException("Customer id on the path must match the id on the customer object");
        }
        service.updateCustomer(cvm);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCustomer(@PathVariable("id") int customerId){
        service.removeCustomer(customerId);
    }
}























