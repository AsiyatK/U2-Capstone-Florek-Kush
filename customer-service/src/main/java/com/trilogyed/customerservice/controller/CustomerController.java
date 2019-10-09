package com.trilogyed.customerservice.controller;


import com.trilogyed.customerservice.exception.NotFoundException;
import com.trilogyed.customerservice.model.Customer;
import com.trilogyed.customerservice.model.CustomerViewModel;
import com.trilogyed.customerservice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    ServiceLayer service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerViewModel createCustomer(@RequestBody @Valid Customer customer){
        return service.saveCustomer(customer);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerViewModel> getAllCustomers(){
        return service.findAllCustomers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerViewModel getCustomer(@PathVariable("id") int id){
        CustomerViewModel customerVM = service.findCustomerById(id);
        if(customerVM == null){
            throw new NotFoundException("Customer with id " + id + " not found");
        }
        return customerVM;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@PathVariable("id") int id, @RequestBody @Valid Customer customer){
        if(customer.getCustomerId() == 0){
            customer.setCustomerId(id);
        }
        if(id != customer.getCustomerId()){
            throw new IllegalArgumentException("Customer id on the path must match the id on the Customer objedt");
        }
        service.updateCustomer(customer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") int id){
        service.removeCustomer(id);
    }


}






