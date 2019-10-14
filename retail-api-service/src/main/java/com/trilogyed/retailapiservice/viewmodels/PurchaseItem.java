package com.trilogyed.retailapiservice.viewmodels;

import com.trilogyed.retailapiservice.viewmodels.backing.InvoiceItemViewModel;

import java.math.BigDecimal;
import java.util.Objects;

public class PurchaseItem {

    private String productName;
    private String productDescription;
    private InvoiceItemViewModel invoiceItem;
    private BigDecimal lineTotal;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public InvoiceItemViewModel getInvoiceItem() {
        return invoiceItem;
    }

    public void setInvoiceItem(InvoiceItemViewModel invoiceItem) {
        this.invoiceItem = invoiceItem;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseItem that = (PurchaseItem) o;
        return Objects.equals(getProductName(), that.getProductName()) &&
                Objects.equals(getProductDescription(), that.getProductDescription()) &&
                Objects.equals(getInvoiceItem(), that.getInvoiceItem()) &&
                Objects.equals(getLineTotal(), that.getLineTotal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductName(), getProductDescription(), getInvoiceItem(), getLineTotal());
    }
}
