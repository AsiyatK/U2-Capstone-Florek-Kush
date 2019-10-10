package com.trilogyed.invoiceservice.dao;

import com.trilogyed.invoiceservice.models.Invoice;
import com.trilogyed.invoiceservice.models.InvoiceItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceItemDaoTest {

    @Autowired
    InvoiceItemDao invoiceItemDao;
    @Autowired
    InvoiceDao invoiceDao;

    @Before
    public void setUp(){
        List<InvoiceItem> itemList = invoiceItemDao.getAllInvoiceItems();
        itemList.stream()
                .forEach(item -> invoiceItemDao.deleteInvoiceItem(item.getInvoiceItemId()));

        List<Invoice> invoiceList = invoiceDao.getAllInvoices();
        invoiceList.stream()
                .forEach(invoice -> invoiceDao.deleteInvoice(invoice.getInvoiceId()));
    }

    @After
    public void tearDown(){
        List<InvoiceItem> itemList = invoiceItemDao.getAllInvoiceItems();
        itemList.stream()
                .forEach(item -> invoiceItemDao.deleteInvoiceItem(item.getInvoiceItemId()));

        List<Invoice> invoiceList = invoiceDao.getAllInvoices();
        invoiceList.stream()
                .forEach(invoice -> invoiceDao.deleteInvoice(invoice.getInvoiceId()));
    }

    @Test
    public void addGetGetByInvoiceDeleteInvoiceItem(){
        //add, get
        Invoice invoice = new Invoice();
        invoice.setCustomerId(732);
        invoice.setPurchaseDate(LocalDate.of(2019, 8, 2));

        invoice = invoiceDao.addInvoice(invoice);

        InvoiceItem item = new InvoiceItem();
        item.setInvoiceId(invoice.getInvoiceId());
        item.setInventoryId(732);
        item.setQuantity(10);
        item.setUnitPrice(new BigDecimal("19.99"));

        item = invoiceItemDao.addInvoiceItem(item);

        InvoiceItem itemFromDb = invoiceItemDao.getInvoiceItem(item.getInvoiceItemId());

        assertEquals(item, itemFromDb);

        //get by invoiceId
        List<InvoiceItem> itemList = new ArrayList<>();
        itemList.add(item);

        List<InvoiceItem> itemsByInvoice = invoiceItemDao.getInvoiceItemsByInvoice(item.getInvoiceId());

        assertEquals(itemList, itemsByInvoice);

        //delete
        invoiceItemDao.deleteInvoiceItem(item.getInvoiceItemId());

        item = invoiceItemDao.getInvoiceItem(item.getInvoiceItemId());

        assertNull(item);

    }

    @Test
    public void updateInvoiceItem(){
        Invoice invoice = new Invoice();
        invoice.setCustomerId(732);
        invoice.setPurchaseDate(LocalDate.of(2019, 8, 2));

        invoice = invoiceDao.addInvoice(invoice);

        InvoiceItem item = new InvoiceItem();
        item.setInvoiceId(invoice.getInvoiceId());
        item.setInventoryId(732);
        item.setQuantity(10);
        item.setUnitPrice(new BigDecimal("19.99"));

        item = invoiceItemDao.addInvoiceItem(item);

        //update in local memory
        item.setUnitPrice(new BigDecimal("11.99"));

        //set new instance equal to db version
        InvoiceItem itemFromDb = invoiceItemDao.getInvoiceItem(item.getInvoiceItemId());

        assertNotEquals(item, itemFromDb);

        invoiceItemDao.updateInvoiceItem(item);

        itemFromDb = invoiceItemDao.getInvoiceItem(item.getInvoiceItemId());

        assertEquals(item, itemFromDb);

    }

    @Test
    public void deleteByInvoice(){
        Invoice invoice = new Invoice();
        invoice.setCustomerId(732);
        invoice.setPurchaseDate(LocalDate.of(2019, 8, 2));

        invoice = invoiceDao.addInvoice(invoice);
        //assemble
        InvoiceItem item = new InvoiceItem();
        item.setInvoiceId(invoice.getInvoiceId());
        item.setInventoryId(732);
        item.setQuantity(10);
        item.setUnitPrice(new BigDecimal("19.99"));
        //add to db
        item = invoiceItemDao.addInvoiceItem(item);
        //add to list
        List<InvoiceItem> itemList = new ArrayList<>();
        itemList.add(item);
        //get list from db
        List<InvoiceItem> itemsByInvoice = invoiceItemDao.getInvoiceItemsByInvoice(item.getInvoiceId());
        //assert lists match/list from db size is as expected
        assertEquals(itemList, itemsByInvoice);
        assertEquals(1, itemsByInvoice.size());

        //act (delete by...)
        invoiceItemDao.deleteInvoiceItemsByInvoice(item.getInvoiceId());
        //get list from db again
        itemsByInvoice = invoiceItemDao.getInvoiceItemsByInvoice(item.getInvoiceId());
        //assert list is now zero
        assertEquals(0, itemsByInvoice.size());
    }

    @Test
    public void getAllInvoiceItems(){
        Invoice invoice = new Invoice();
        invoice.setCustomerId(732);
        invoice.setPurchaseDate(LocalDate.of(2019, 8, 2));

        invoice = invoiceDao.addInvoice(invoice);

        List<InvoiceItem> itemList = invoiceItemDao.getAllInvoiceItems();

        assertEquals(itemList.size(), 0);

        InvoiceItem item = new InvoiceItem();
        item.setInvoiceId(invoice.getInvoiceId());
        item.setInventoryId(732);
        item.setQuantity(10);
        item.setUnitPrice(new BigDecimal("19.99"));

        invoiceItemDao.addInvoiceItem(item);

        itemList = invoiceItemDao.getAllInvoiceItems();

        assertEquals(itemList.size(), 1);

        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(908);
        invoice1.setPurchaseDate(LocalDate.of(2019, 7, 19));

        invoiceDao.addInvoice(invoice1);

        item = new InvoiceItem();
        item.setInvoiceId(invoice1.getInvoiceId());
        item.setInventoryId(908);
        item.setQuantity(82);
        item.setUnitPrice(new BigDecimal("10.99"));

        invoiceItemDao.addInvoiceItem(item);

        itemList = invoiceItemDao.getAllInvoiceItems();

        assertEquals(itemList.size(), 2);

    }

}
