package com.trilogyed.customerservice.service;


import com.trilogyed.customerservice.dao.CustomerDao;
import com.trilogyed.customerservice.model.Customer;
import com.trilogyed.customerservice.model.CustomerViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceLayer {
    private CustomerDao dao;

    @Autowired
    public ServiceLayer(CustomerDao dao) {
        this.dao = dao;
    }

    @Transactional
    public CustomerViewModel saveCustomer(Customer customer){
        customer = dao.addCustomer(customer);
        return buildCustomerViewModel(customer);
    }

    public CustomerViewModel findCustomerById(int customerId){
        Customer customer = dao.getCustomer(customerId);
        if(customer == null){
            return null;
        } else {
            return buildCustomerViewModel(customer);
        }
    }


    public List<CustomerViewModel> findAllCustomers(){
        List<Customer> customers = dao.getAllCustomers();
        List<CustomerViewModel> cvms = new ArrayList<>();

        customers.stream()
                .forEach(customer -> cvms.add(buildCustomerViewModel(customer)));
        return cvms;
    }

    @Transactional
    public void updateCustomer (Customer customer){
        dao.updateCustomer(customer);
    }

    @Transactional
    public void removeCustomer(int customerId){
        dao.deleteCustomer(customerId);
    }



   // to build customer view model
    private CustomerViewModel buildCustomerViewModel(Customer customer) {
        CustomerViewModel cvm = new CustomerViewModel();
        cvm.setCustomerId(customer.getCustomerId());
        cvm.setFirstName(customer.getFirstName());
        cvm.setLastName(customer.getLastName());
        cvm.setStreet(customer.getStreet());
        cvm.setCity(customer.getCity());
        cvm.setZip(customer.getZip());
        cvm.setEmail(customer.getEmail());
        cvm.setPhone(customer.getPhone());

        return cvm;
    }
}
