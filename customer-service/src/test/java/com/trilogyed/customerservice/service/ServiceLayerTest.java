package com.trilogyed.customerservice.service;

import com.trilogyed.customerservice.dao.CustomerDao;
import com.trilogyed.customerservice.dao.CustomerDaoJdbcTemplateImpl;
import com.trilogyed.customerservice.model.Customer;
import com.trilogyed.customerservice.model.CustomerViewModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {

    ServiceLayer service;
    CustomerDao customerDao;

    @Before
    public void setUp() throws Exception{
        setUpCustomerDaoMock();

        service = new ServiceLayer(customerDao);
    }


    @Test
    public void saveFindFindAllCustomer() {
        CustomerViewModel customerVM = new CustomerViewModel();
        customerVM.setCustomerId(45);
        customerVM.setFirstName("Amelia");
        customerVM.setLastName("Earhart");
        customerVM.setStreet("123 Ocean Ave");
        customerVM.setCity("Atchison");
        customerVM.setZip("09345");
        customerVM.setEmail("ae@ameliaearhart.com");
        customerVM.setPhone("333-777-8899");

        Customer customer = new Customer();
        customer.setFirstName("Amelia");
        customer.setLastName("Earhart");
        customer.setStreet("123 Ocean Ave");
        customer.setCity("Atchison");
        customer.setZip("09345");
        customer.setEmail("ae@ameliaearhart.com");
        customer.setPhone("333-777-8899");

        customerVM = service.saveCustomer(customer); //<-- save

        CustomerViewModel fromService = service.findCustomerById(customerVM.getCustomerId()); //<--find
        assertEquals(customerVM, fromService);


        List<CustomerViewModel> customerVMs = service.findAllCustomers();//<-- find all
        assertEquals(customerVMs.size(), 1);
        assertEquals(customerVM, customerVMs.get(0));
    }


    @Test

    public void updateCustomer() {
        Customer customerUpdate = new Customer();
        customerUpdate.setCustomerId(1);
        customerUpdate.setFirstName("Name Updated");
        customerUpdate.setLastName("Updated");
        customerUpdate.setStreet("Street Updated");
        customerUpdate.setCity("City Updated");
        customerUpdate.setZip("88888");
        customerUpdate.setEmail("ae@updated.com");
        customerUpdate.setPhone("333-777-8899");

        service.updateCustomer(customerUpdate);

        Customer customer = customerDao.getCustomer(1);
        assertEquals(customer, customerUpdate);

    }

    @Test
    public void removeCustomer() {
        service.removeCustomer(3);
        CustomerViewModel customerVM = service.findCustomerById(3);
        assertNull(customerVM);
    }




    private void setUpCustomerDaoMock(){
        customerDao = mock(CustomerDaoJdbcTemplateImpl.class);

        //mock the add
        Customer customer = new Customer();
        customer.setCustomerId(45);
        customer.setFirstName("Amelia");
        customer.setLastName("Earhart");
        customer.setStreet("123 Ocean Ave");
        customer.setCity("Atchison");
        customer.setZip("09345");
        customer.setEmail("ae@ameliaearhart.com");
        customer.setPhone("333-777-8899");

        Customer customer2 = new Customer();
        customer2.setFirstName("Amelia");
        customer2.setLastName("Earhart");
        customer2.setStreet("123 Ocean Ave");
        customer2.setCity("Atchison");
        customer2.setZip("09345");
        customer2.setEmail("ae@ameliaearhart.com");
        customer2.setPhone("333-777-8899");

        doReturn(customer).when(customerDao).addCustomer(customer2);

        //mock the get customer by id
        doReturn(customer).when(customerDao).getCustomer(45);

        //mock get all
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        doReturn(customers).when(customerDao).getAllCustomers();

        //mock the update
        Customer customerUpdate = new Customer();
        customerUpdate.setCustomerId(1);
        customerUpdate.setFirstName("Name Updated");
        customerUpdate.setLastName("Updated");
        customerUpdate.setStreet("Street Updated");
        customerUpdate.setCity("City Updated");
        customerUpdate.setZip("88888");
        customerUpdate.setEmail("ae@updated.com");
        customerUpdate.setPhone("333-777-8899");

        doNothing().when(customerDao).updateCustomer(customerUpdate);
        doReturn(customerUpdate).when(customerDao).getCustomer(1);

        //mock the delete

        Customer customerDelete = new Customer();
        customerDelete.setCustomerId(3);
        customerDelete.setFirstName("For deletion");
        customerDelete.setLastName("To be deleted");
        customerDelete.setStreet("Street deleted");
        customerDelete.setCity("City deleted");
        customerDelete.setZip("88888");
        customerDelete.setEmail("ae@updated.com");
        customerDelete.setPhone("333-777-8899");

        doNothing().when(customerDao).deleteCustomer(3);
        doReturn(null).when(customerDao).getCustomer(3);
    }

}























