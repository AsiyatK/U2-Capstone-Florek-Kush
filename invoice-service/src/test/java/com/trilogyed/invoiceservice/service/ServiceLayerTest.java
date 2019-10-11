package com.trilogyed.invoiceservice.service;


import com.trilogyed.invoiceservice.dao.InvoiceDao;
import com.trilogyed.invoiceservice.dao.InvoiceDaoJdbcTemplateImpl;
import com.trilogyed.invoiceservice.dao.InvoiceItemDao;
import com.trilogyed.invoiceservice.dao.InvoiceItemDaoJdbcTemplateImpl;
import com.trilogyed.invoiceservice.models.Invoice;
import org.junit.Before;

import static org.mockito.Mockito.mock;

public class ServiceLayerTest {

    ServiceLayer sl;
    InvoiceDao invoiceDao;
    InvoiceItemDao itemDao;

    @Before
    public void setUp() throws Exception {
        setUpInvoiceMock();
        setUpItemMock();

        sl = new ServiceLayer(invoiceDao, itemDao);
    }

    public void addGetInvoiceGetByCustomerDelete(){

    }

    public void getAllInvoices(){

    }

    public void updateInvoice(){

    }

    public void addGetDeleteItem(){

    }

    public void updateItem(){

    }

    public void setUpInvoiceMock(){
        invoiceDao = mock(InvoiceDaoJdbcTemplateImpl.class);

        Invoice invoiceAdded = new Invoice();


    }

    public void setUpItemMock(){
        itemDao = mock(InvoiceItemDaoJdbcTemplateImpl.class);


    }
}
