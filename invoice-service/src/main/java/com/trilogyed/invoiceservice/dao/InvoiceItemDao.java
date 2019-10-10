package com.trilogyed.invoiceservice.dao;

import com.trilogyed.invoiceservice.models.InvoiceItem;

import java.util.List;

public interface InvoiceItemDao {

    InvoiceItem addInvoiceItem(InvoiceItem item);

    InvoiceItem getInvoiceItem(int invoiceItemId);

    List<InvoiceItem> getInvoiceItemsByInvoice(int invoiceId);

    List<InvoiceItem> getAllInvoiceItems();

    void updateInvoiceItem(InvoiceItem item);

    void deleteInvoiceItem(int invoiceItemId);

    void deleteInvoiceItemsByInvoice(int invoiceId);

}
