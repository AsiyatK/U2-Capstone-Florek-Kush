package com.trilogyed.customerservice.dao;

import com.trilogyed.customerservice.model.Customer;

import java.util.List;

public interface CustomerDao {

    //create
    Customer addCustomer(Customer customer);
    //read
    Customer getCustomer(int customerId);
    //read all
    List<Customer> getAllCustomers();
    //update
    void updateCustomer(Customer customer);
    //delete
    void deleteCustomer(int customerId);

}
