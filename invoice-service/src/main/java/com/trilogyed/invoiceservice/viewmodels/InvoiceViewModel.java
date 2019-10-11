package com.trilogyed.invoiceservice.viewmodels;

import com.trilogyed.invoiceservice.models.Invoice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InvoiceViewModel extends Invoice {

    private List<InvoiceItemViewModel> invoiceItems = new ArrayList<>();

    public List<InvoiceItemViewModel> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItemViewModel> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        InvoiceViewModel that = (InvoiceViewModel) o;
        return Objects.equals(getInvoiceItems(), that.getInvoiceItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getInvoiceItems());
    }
}
