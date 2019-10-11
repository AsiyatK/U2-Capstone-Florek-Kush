package com.trilogyed.invoiceservice.service;

import com.trilogyed.invoiceservice.dao.InvoiceDao;
import com.trilogyed.invoiceservice.dao.InvoiceItemDao;
import com.trilogyed.invoiceservice.exceptions.InvoiceNotFoundException;
import com.trilogyed.invoiceservice.models.Invoice;
import com.trilogyed.invoiceservice.models.InvoiceItem;
import com.trilogyed.invoiceservice.viewmodels.InvoiceItemViewModel;
import com.trilogyed.invoiceservice.viewmodels.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public void deleteInvoice(int invoiceId){

        itemDao.deleteInvoiceItemsByInvoice(invoiceId);
        invoiceDao.deleteInvoice(invoiceId);

    }

    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice){
        InvoiceViewModel invoiceModel = new InvoiceViewModel();
        invoiceModel.setInvoiceId(invoice.getInvoiceId());
        invoiceModel.setCustomerId(invoice.getCustomerId());
        invoiceModel.setPurchaseDate(invoice.getPurchaseDate());
        invoiceModel.setInvoiceItems(itemDao.getInvoiceItemsByInvoice(invoice.getInvoiceId()));

        return invoiceModel;

    }

    private InvoiceItemViewModel buildInvoiceItemViewModel(InvoiceItem item){
        InvoiceItemViewModel itemModel = new InvoiceItemViewModel();
        itemModel.setInvoiceItemId();
    }

}
