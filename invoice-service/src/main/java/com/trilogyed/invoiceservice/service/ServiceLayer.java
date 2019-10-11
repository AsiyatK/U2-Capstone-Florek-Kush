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

import java.util.ArrayList;
import java.util.List;

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
    //*************************TO DO*************************\\
    //Should make only two calls (one to Invoice table and one to Item table, then use filtered list to get InvoiceItems
    public List<InvoiceViewModel> getAllInvoices(){

        List<Invoice> invoiceList = invoiceDao.getAllInvoices();

        List<InvoiceViewModel> invoiceModels = new ArrayList<>();

        invoiceList.stream()
                .forEach(invoice -> {
                    InvoiceViewModel model = buildInvoiceViewModel(invoice);
                    invoiceModels.add(model);
                });

        return invoiceModels;

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

    public List<InvoiceItemViewModel> getAllInvoiceItems(){
        List<InvoiceItem> itemList = itemDao.getAllInvoiceItems();

        List<InvoiceItemViewModel> itemModels = new ArrayList<>();

        itemList.stream()
                .forEach(item -> {
                    InvoiceItemViewModel model = buildInvoiceItemViewModel(item);
                    itemModels.add(model);
                });

        return itemModels;

    }
    //possible unnecessary to have this method...
    //similar to deleteByInvoice, dao method getByInvoice could be called only within getInvoice methods as needed
    public List<InvoiceItemViewModel> getInvoiceItemsByInvoice(int invoiceId){
        List<InvoiceItem> itemList = itemDao.getInvoiceItemsByInvoice(invoiceId);

        List<InvoiceItemViewModel> itemModels = new ArrayList<>();

        itemList.stream()
                .forEach(item -> {
                    InvoiceItemViewModel model = buildInvoiceItemViewModel(item);
                    itemModels.add(model);
                });

        return itemModels;

    }

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
