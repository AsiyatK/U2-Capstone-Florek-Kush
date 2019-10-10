package com.trilogyed.invoiceservice.dao;

import com.trilogyed.invoiceservice.models.Invoice;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceDaoTest {

    @Autowired
    InvoiceDao invoiceDao;

    @Before
    public void setUp(){
        List<Invoice> invoiceList = invoiceDao.getAllInvoices();
        invoiceList.stream()
                .forEach(invoice -> invoiceDao.deleteInvoice(invoice.getInvoiceId()));
    }

    @After
    public void tearDown(){
        List<Invoice> invoiceList = invoiceDao.getAllInvoices();
        invoiceList.stream()
                .forEach(invoice -> invoiceDao.deleteInvoice(invoice.getInvoiceId()));
    }

    @Test
    public void addGetGetByCustomerDeleteInvoice(){
        //add, get
        Invoice invoice = new Invoice();
        invoice.setCustomerId(732);
        invoice.setPurchaseDate(LocalDate.of(2019, 8, 2));

        invoice = invoiceDao.addInvoice(invoice);

        Invoice invoiceFromDb = invoiceDao.getInvoice(invoice.getInvoiceId());

        assertEquals(invoice, invoiceFromDb);

        //get by customerId
        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);

        List<Invoice> invoicesByCustomer = invoiceDao.getInvoicesByCustomer(invoice.getCustomerId());

        assertEquals(invoiceList, invoicesByCustomer);

        //delete
        invoiceDao.deleteInvoice(invoice.getInvoiceId());

        invoice = invoiceDao.getInvoice(invoice.getInvoiceId());

        assertNull(invoice);

    }

    @Test
    public void updateInvoice(){

        Invoice invoice = new Invoice();
        invoice.setCustomerId(732);
        invoice.setPurchaseDate(LocalDate.of(2019, 8, 2));

        invoice = invoiceDao.addInvoice(invoice);

        //update in local memory
        invoice.setCustomerId(908);

        //set new instance equal to db version
        Invoice invoiceFromDb = invoiceDao.getInvoice(invoice.getInvoiceId());

        assertNotEquals(invoice, invoiceFromDb);

        invoiceDao.updateInvoice(invoice);

        invoiceFromDb = invoiceDao.getInvoice(invoice.getInvoiceId());

        assertEquals(invoice, invoiceFromDb);

    }

    @Test
    public void getAllInvoices(){

        List<Invoice> invoiceList = invoiceDao.getAllInvoices();

        assertEquals(0, invoiceList.size());

        Invoice invoice = new Invoice();
        invoice.setCustomerId(732);
        invoice.setPurchaseDate(LocalDate.of(2019, 8, 2));

        invoiceDao.addInvoice(invoice);

        invoiceList = invoiceDao.getAllInvoices();

        assertEquals(1, invoiceList.size());

        invoice = new Invoice();
        invoice.setCustomerId(908);
        invoice.setPurchaseDate(LocalDate.of(2019, 7, 19));

        invoiceDao.addInvoice(invoice);

        invoiceList = invoiceDao.getAllInvoices();

        assertEquals(2, invoiceList.size());

    }
}
