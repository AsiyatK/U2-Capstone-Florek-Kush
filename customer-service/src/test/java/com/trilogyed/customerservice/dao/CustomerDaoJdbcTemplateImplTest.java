package com.trilogyed.customerservice.dao;

import com.trilogyed.customerservice.model.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomerDaoJdbcTemplateImplTest {

    @Autowired
    protected CustomerDao customerDao;

    @Before
    public void setUp() throws Exception {
        List<Customer> customers = customerDao.getAllCustomers();
        customers.forEach(customer -> customerDao.deleteCustomer(customer.getCustomerId()));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addGetDeleteCustomer() {
        //Arrange
        Customer customer = new Customer();
        customer.setFirstName("Amelia");
        customer.setLastName("Earhart");
        customer.setStreet("123 Ocean Ave");
        customer.setCity("Atchison");
        customer.setZip("09345");
        customer.setEmail("ae@ameliaearhart.com");
        customer.setPhone("333-777-8899");

        //Act
        customer = customerDao.addCustomer(customer);
        Customer customer1 = customerDao.getCustomer(customer.getCustomerId());

        //Assert
        assertEquals(customer, customer1);

        customerDao.deleteCustomer(customer.getCustomerId());
        customer1 = customerDao.getCustomer(customer.getCustomerId());
        assertNull(customer1);

    }


    @Test
    public void getAllCustomers() {

        Customer customer = new Customer();
        customer.setFirstName("Amelia");
        customer.setLastName("Earhart");
        customer.setStreet("123 Ocean Ave");
        customer.setCity("Atchison");
        customer.setZip("09345");
        customer.setEmail("ae@ameliaearhart.com");
        customer.setPhone("333-777-8899");
        customerDao.addCustomer(customer);

        customer = new Customer();
        customer.setFirstName("Ada");
        customer.setLastName("Lovelace");
        customer.setStreet("123 Stoneview Dr");
        customer.setCity("Trenton");
        customer.setZip("88990");
        customer.setEmail("al@mathematics.com");
        customer.setPhone("889-998-1111");
        customerDao.addCustomer(customer);

        List<Customer> customers = customerDao.getAllCustomers();
        assertEquals(customers.size(), 2);
    }

    @Test
    public void updateCustomer() {

        Customer customer = new Customer();
        customer.setFirstName("Amelia");
        customer.setLastName("Earhart");
        customer.setStreet("123 Ocean Ave");
        customer.setCity("Atchison");
        customer.setZip("09345");
        customer.setEmail("ae@ameliaearhart.com");
        customer.setPhone("333-777-8899");
        customerDao.addCustomer(customer);

        customer.setCity("Annapolis");
        customer.setZip("77665");
        customer.setPhone("657-771-5566");

        customerDao.updateCustomer(customer);

        Customer customer1 = customerDao.getCustomer(customer.getCustomerId());
        assertEquals(customer, customer1);
    }


}















