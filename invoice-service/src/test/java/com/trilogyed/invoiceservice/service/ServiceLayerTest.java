package com.trilogyed.invoiceservice.service;


import com.trilogyed.invoiceservice.dao.InvoiceDao;
import com.trilogyed.invoiceservice.dao.InvoiceDaoJdbcTemplateImpl;
import com.trilogyed.invoiceservice.dao.InvoiceItemDao;
import com.trilogyed.invoiceservice.dao.InvoiceItemDaoJdbcTemplateImpl;
import com.trilogyed.invoiceservice.models.Invoice;
import com.trilogyed.invoiceservice.models.InvoiceItem;
import com.trilogyed.invoiceservice.viewmodels.InvoiceItemViewModel;
import com.trilogyed.invoiceservice.viewmodels.InvoiceViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

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

    @Test
    public void addGetInvoiceGetByCustomerAddGetItemGetByInvoice(){
        //Assemble
        InvoiceViewModel invoice = new InvoiceViewModel();
        invoice.setCustomerId(732);
        invoice.setPurchaseDate(LocalDate.of(2019, 8, 2));
        //Act
        invoice = sl.createInvoice(invoice);
        //Assemble
        InvoiceItemViewModel item = new InvoiceItemViewModel();
        item.setInvoiceId(invoice.getInvoiceId());
        item.setInventoryId(732);
        item.setQuantity(82);
        item.setUnitPrice(new BigDecimal("19.99"));
        //Act
        item = sl.createInvoiceItem(item);
        //Assemble
        List<InvoiceItemViewModel> itemList = new ArrayList<>();
        itemList.add(item);
        invoice.setInvoiceItems(itemList);
        //Act
        InvoiceViewModel invoiceFromService = sl.getInvoice(invoice.getInvoiceId());
        //Assert
        assertEquals(invoice, invoiceFromService);

        //Assemble
        List<InvoiceViewModel> invoices = new ArrayList<>();
        invoices.add(invoice);
        //Act
        List<InvoiceViewModel> invoicesByCustomer = sl.getInvoicesByCustomer(invoice.getCustomerId());
        //Assert
        assertEquals(invoices, invoicesByCustomer);
        assertEquals(1, invoicesByCustomer.size());

    }

    @Test
    public void getAllInvoices(){
        //Assemble first Invoice with associated item
        InvoiceViewModel invoice = new InvoiceViewModel();
        invoice.setInvoiceId(732);
        invoice.setCustomerId(732);
        invoice.setPurchaseDate(LocalDate.of(2019, 8, 2));

        //Assemble
        InvoiceItemViewModel item = new InvoiceItemViewModel();
        item.setInvoiceItemId(732);
        item.setInvoiceId(invoice.getInvoiceId());
        item.setInventoryId(732);
        item.setQuantity(82);
        item.setUnitPrice(new BigDecimal("19.99"));

        //Assemble
        List<InvoiceItemViewModel> itemList = new ArrayList<>();
        itemList.add(item);
        invoice.setInvoiceItems(itemList);
        //Assemble
        List<InvoiceViewModel> invoices = new ArrayList<>();
        invoices.add(invoice);

        //Assemble second Invoice with associated item
        invoice = new InvoiceViewModel();
        invoice.setInvoiceId(908);
        invoice.setCustomerId(908);
        invoice.setPurchaseDate(LocalDate.of(2019, 7, 19));

        //Assemble
        item = new InvoiceItemViewModel();
        item.setInvoiceItemId(908);
        item.setInvoiceId(invoice.getInvoiceId());
        item.setInventoryId(908);
        item.setQuantity(87);
        item.setUnitPrice(new BigDecimal("14.99"));

        //Assemble
        itemList = new ArrayList<>();
        itemList.add(item);
        invoice.setInvoiceItems(itemList);
        //Assemble
        invoices.add(invoice);

        //Act
        List<InvoiceViewModel> invoicesFromService = sl.getAllInvoices();

        //Assert
        assertEquals(invoices, invoicesFromService);
        assertEquals(2, invoicesFromService.size());
    }

    @Test
    public void updateInvoice(){
        InvoiceViewModel invoiceUpdated = new InvoiceViewModel();
        invoiceUpdated.setInvoiceId(908);
        invoiceUpdated.setCustomerId(98);
        invoiceUpdated.setPurchaseDate(LocalDate.of(2019, 7, 19));

        InvoiceItemViewModel itemUpdated = new InvoiceItemViewModel();
        itemUpdated.setInvoiceItemId(908);
        itemUpdated.setInvoiceId(908);
        itemUpdated.setInventoryId(908);
        itemUpdated.setQuantity(87);
        itemUpdated.setUnitPrice(new BigDecimal("14.99"));

        List<InvoiceItemViewModel> itemList = new ArrayList<>();
        itemList.add(itemUpdated);
        invoiceUpdated.setInvoiceItems(itemList);

        invoiceUpdated.setCustomerId(908);

        sl.updateInvoice(invoiceUpdated);

        InvoiceViewModel invoiceFromService = sl.getInvoice(invoiceUpdated.getInvoiceId());

        assertEquals(invoiceUpdated, invoiceFromService);

    }

    @Test
    public void deleteInvoice(){

    }

    public void addItem(){

    }

    public void updateItem(){

    }

    public void setUpInvoiceMock(){
        invoiceDao = mock(InvoiceDaoJdbcTemplateImpl.class);

        Invoice invoiceAdded = new Invoice();
        invoiceAdded.setInvoiceId(732);
        invoiceAdded.setCustomerId(732);
        invoiceAdded.setPurchaseDate(LocalDate.of(2019, 8, 2));

        InvoiceViewModel invoiceNew = new InvoiceViewModel();
        invoiceNew.setCustomerId(732);
        invoiceNew.setPurchaseDate(LocalDate.of(2019, 8, 2));

        doReturn(invoiceAdded).when(invoiceDao).addInvoice(invoiceNew);
        doReturn(invoiceAdded).when(invoiceDao).getInvoice(732);

        List<Invoice> invoicesByCustomer = new ArrayList<>();
        invoicesByCustomer.add(invoiceAdded);

        doReturn(invoicesByCustomer).when(invoiceDao).getInvoicesByCustomer(732);

        Invoice invoiceUpdated = new Invoice();
        invoiceUpdated.setInvoiceId(908);
        invoiceUpdated.setCustomerId(908);
        invoiceUpdated.setPurchaseDate(LocalDate.of(2019, 7, 19));

        doNothing().when(invoiceDao).updateInvoice(invoiceUpdated);
        doReturn(invoiceUpdated).when(invoiceDao).getInvoice(908);

        Invoice invoiceDeleted = new Invoice();
        invoiceDeleted.setInvoiceId(973);
        invoiceDeleted.setCustomerId(973);
        invoiceDeleted.setPurchaseDate(LocalDate.of(2019, 6, 25));

        doNothing().when(invoiceDao).deleteInvoice(973);
        doReturn(null).when(invoiceDao).getInvoice(973);
        List<Invoice> emptyList = new ArrayList<>();
        doReturn(emptyList).when(invoiceDao).getInvoicesByCustomer(973);

        List<Invoice> allInvoices = new ArrayList<>();
        allInvoices.add(invoiceAdded);
        allInvoices.add(invoiceUpdated);
        doReturn(allInvoices).when(invoiceDao).getAllInvoices();

    }

    public void setUpItemMock(){
        itemDao = mock(InvoiceItemDaoJdbcTemplateImpl.class);

        InvoiceItem itemAdded = new InvoiceItem();
        itemAdded.setInvoiceItemId(732);
        itemAdded.setInvoiceId(732);
        itemAdded.setInventoryId(732);
        itemAdded.setQuantity(82);
        itemAdded.setUnitPrice(new BigDecimal("19.99"));

        InvoiceItemViewModel itemNew = new InvoiceItemViewModel();
        itemNew.setInvoiceId(732);
        itemNew.setInventoryId(732);
        itemNew.setQuantity(82);
        itemNew.setUnitPrice(new BigDecimal("19.99"));

        doReturn(itemAdded).when(itemDao).addInvoiceItem(itemNew);
        doReturn(itemAdded).when(itemDao).getInvoiceItem(732);

        List<InvoiceItem> itemsByInvoice = new ArrayList<>();
        itemsByInvoice.add(itemAdded);
        doReturn(itemsByInvoice).when(itemDao).getInvoiceItemsByInvoice(732);

        InvoiceItem itemUpdated = new InvoiceItem();
        itemUpdated.setInvoiceItemId(908);
        itemUpdated.setInvoiceId(908);
        itemUpdated.setInventoryId(908);
        itemUpdated.setQuantity(87);
        itemUpdated.setUnitPrice(new BigDecimal("14.99"));

        doNothing().when(itemDao).updateInvoiceItem(itemUpdated);
        doReturn(itemUpdated).when(itemDao).getInvoiceItem(908);

        List<InvoiceItem> itemsByInvoice1 = new ArrayList<>();
        itemsByInvoice1.add(itemUpdated);
        doReturn(itemsByInvoice1).when(itemDao).getInvoiceItemsByInvoice(908);
//        InvoiceItem itemDeleted = new InvoiceItem();
//        itemDeleted.setInvoiceItemId(973);
//        itemDeleted.setInvoiceId(973);
//        itemDeleted.setInventoryId(973);
//        itemDeleted.setQuantity(37);
//        itemDeleted.setUnitPrice(new BigDecimal("11.99"));

        doNothing().when(itemDao).deleteInvoiceItem(973);
        doReturn(null).when(itemDao).getInvoiceItem(973);
        List<InvoiceItem> emptyList = new ArrayList<>();
        doReturn(emptyList).when(itemDao).getInvoiceItemsByInvoice(973);

        List<InvoiceItem> allItems = new ArrayList<>();
        allItems.add(itemAdded);
        allItems.add(itemUpdated);
        doReturn(allItems).when(itemDao).getAllInvoiceItems();

    }
}
