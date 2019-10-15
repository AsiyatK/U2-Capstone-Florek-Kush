package com.trilogyed.invoiceservice.service;

import com.trilogyed.invoiceservice.dao.InvoiceDao;
import com.trilogyed.invoiceservice.dao.InvoiceItemDao;
import com.trilogyed.invoiceservice.exceptions.InvoiceItemNotFoundException;
import com.trilogyed.invoiceservice.exceptions.InvoiceNotFoundException;
import com.trilogyed.invoiceservice.models.Invoice;
import com.trilogyed.invoiceservice.models.InvoiceItem;
import com.trilogyed.invoiceservice.viewmodels.InvoiceItemViewModel;
import com.trilogyed.invoiceservice.viewmodels.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceLayer {

    private InvoiceDao invoiceDao;
    private InvoiceItemDao itemDao;

    @Autowired
    public ServiceLayer(InvoiceDao invoiceDao, InvoiceItemDao itemDao){
        this.invoiceDao=invoiceDao;
        this.itemDao=itemDao;
    }

    /////////////INVOICE METHODS
    public InvoiceViewModel createInvoice(InvoiceViewModel newInvoice){
        newInvoice.setPurchaseDate(LocalDate.now());
        Invoice invoiceCreated = invoiceDao.addInvoice(newInvoice);

        newInvoice.setInvoiceId(invoiceCreated.getInvoiceId());

        return newInvoice;
    }

    public InvoiceViewModel getInvoice(int invoiceId){

        Invoice i = invoiceDao.getInvoice(invoiceId);

        if(i==null){
            throw new InvoiceNotFoundException("Invoice with id " + invoiceId + " could not be located.");
        } else {
            InvoiceViewModel invoice = buildInvoiceViewModel(i);
            if(invoice != null){
                return invoice;
            } else {
                throw new InvoiceNotFoundException("Invoice with id " + invoiceId + " could not be located.");
            }
        }
    }

    public List<InvoiceViewModel> getAllInvoices(){

        List<Invoice> invoiceList = invoiceDao.getAllInvoices();

        List<InvoiceItem> itemList = itemDao.getAllInvoiceItems();

        List<InvoiceViewModel> invoiceModels = new ArrayList<>();

        for (int i = 0; i < invoiceList.size(); i++) {

            int id = invoiceList.get(i).getInvoiceId();

            List<InvoiceItem> itemsByInvoice =
            itemList.stream()
                    .filter(item -> item.getInvoiceId() == id)
                    .collect(Collectors.toList());

            InvoiceViewModel model = buildInvoiceViewModel(invoiceList.get(i), itemsByInvoice);

            invoiceModels.add(model);
        }

        return invoiceModels;

//        List<Invoice> invoiceList = invoiceDao.getAllInvoices();
//
//        List<InvoiceViewModel> invoiceModels = new ArrayList<>();
//
//
//        invoiceList.stream()
//                .forEach(invoice -> {
//                    InvoiceViewModel model = buildInvoiceViewModel(invoice);
//                    invoiceModels.add(model);
//                });

    }

    public List<InvoiceViewModel> getInvoicesByCustomer(int customerId){

        List<Invoice> invoiceList = invoiceDao.getInvoicesByCustomer(customerId);

        List<InvoiceViewModel> invoiceModels = new ArrayList<>();

        invoiceList.stream()
                .forEach(invoice -> {
                    InvoiceViewModel model = buildInvoiceViewModel(invoice);
                    invoiceModels.add(model);
                });

        return invoiceModels;
    }

    public void updateInvoice(InvoiceViewModel updatedInvoice){

        invoiceDao.updateInvoice(updatedInvoice);

    }

    @Transactional
    public void deleteInvoice(int invoiceId){

        itemDao.deleteInvoiceItemsByInvoice(invoiceId);
        invoiceDao.deleteInvoice(invoiceId);

    }

    public InvoiceItemViewModel createInvoiceItem(InvoiceItemViewModel newItem){
        InvoiceItem itemCreated = itemDao.addInvoiceItem(newItem);

        newItem.setInvoiceItemId(itemCreated.getInvoiceItemId());
        System.out.println("InvoiceItem created");
        return newItem;
    }

    public InvoiceItemViewModel getInvoiceItem(int itemId){
        InvoiceItem i = itemDao.getInvoiceItem(itemId);

        if(i==null){
            throw new InvoiceItemNotFoundException("InvoiceItem with id " + itemId + " could not be located.");
        } else {
            InvoiceItemViewModel itemModel = buildInvoiceItemViewModel(i);
            if(itemModel != null){
                return itemModel;
            } else {
                throw new InvoiceItemNotFoundException("InvoiceItem with id " + itemId + " could not be located.");
            }
        }
    }
//************THE FOLLOWING TWO METHODS EXCLUDED AS STAND-ALONE METHODS*************\\
    //*****THE CORRESPONDING DAO METHODS ARE UTILIZED IN GETALLINVOICES() AND GETINVOICE()*****\\
//    public List<InvoiceItemViewModel> getAllInvoiceItems(){
//        List<InvoiceItem> itemList = itemDao.getAllInvoiceItems();
//
//        List<InvoiceItemViewModel> itemModels = new ArrayList<>();
//
//        itemList.stream()
//                .forEach(item -> {
//                    InvoiceItemViewModel model = buildInvoiceItemViewModel(item);
//                    itemModels.add(model);
//                });
//
//        return itemModels;
//
//    }

    //similar to deleteByInvoice, dao method getByInvoice could be called only within getInvoice methods as needed
//    public List<InvoiceItemViewModel> getInvoiceItemsByInvoice(int invoiceId){
//        List<InvoiceItem> itemList = itemDao.getInvoiceItemsByInvoice(invoiceId);
//
//        List<InvoiceItemViewModel> itemModels = new ArrayList<>();
//
//        itemList.stream()
//                .forEach(item -> {
//                    InvoiceItemViewModel model = buildInvoiceItemViewModel(item);
//                    itemModels.add(model);
//                });
//
//        return itemModels;
//
//    }

    public void updateInvoiceItem(InvoiceItemViewModel updatedItem){

        itemDao.updateInvoiceItem(updatedItem);
    }

    public void deleteInvoiceItem(int itemId){

        itemDao.deleteInvoiceItem(itemId);

    }

    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice){
        InvoiceViewModel invoiceModel = new InvoiceViewModel();
        invoiceModel.setInvoiceId(invoice.getInvoiceId());
        invoiceModel.setCustomerId(invoice.getCustomerId());
        invoiceModel.setPurchaseDate(invoice.getPurchaseDate());
        //call getItemsByInvoice method then call buildItem method
        List<InvoiceItem> items = itemDao.getInvoiceItemsByInvoice(invoice.getInvoiceId());
        invoiceModel.setInvoiceItems(buildInvoiceItemViewModel(items));

        return invoiceModel;

    }

    //TAKES IN AN INVOICE OBJECT AND A LIST OF INVOICE ITEMS
    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice, List<InvoiceItem> items){
        InvoiceViewModel invoiceModel = new InvoiceViewModel();
        invoiceModel.setInvoiceId(invoice.getInvoiceId());
        invoiceModel.setCustomerId(invoice.getCustomerId());
        invoiceModel.setPurchaseDate(invoice.getPurchaseDate());

        invoiceModel.setInvoiceItems(buildInvoiceItemViewModel(items));

        return invoiceModel;

    }

    private InvoiceItemViewModel buildInvoiceItemViewModel(InvoiceItem item){
        InvoiceItemViewModel itemModel = new InvoiceItemViewModel();
        itemModel.setInvoiceItemId(item.getInvoiceItemId());
        itemModel.setInvoiceId(item.getInvoiceId());
        itemModel.setInventoryId(item.getInventoryId());
        itemModel.setQuantity(item.getQuantity());
        itemModel.setUnitPrice(item.getUnitPrice());

        return itemModel;
    }

    private List<InvoiceItemViewModel> buildInvoiceItemViewModel(List<InvoiceItem> items){
        List<InvoiceItemViewModel> itemModels = new ArrayList<>();

        items.stream()
                .forEach(item -> {
                    InvoiceItemViewModel itemModel = buildInvoiceItemViewModel(item);
                    itemModels.add(itemModel);
                });

        return itemModels;
    }


}
