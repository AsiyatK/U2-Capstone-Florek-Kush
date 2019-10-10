package com.trilogyed.invoiceservice.models;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Objects;

public class Invoice {

    private int invoiceId;
    @NotBlank(message = "customerId is a required field")
    private int customerId;
    @NotBlank(message = "purchaseDate is a required field.")
    private LocalDate purchaseDate;

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return getInvoiceId() == invoice.getInvoiceId() &&
                getCustomerId() == invoice.getCustomerId() &&
                getPurchaseDate().equals(invoice.getPurchaseDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInvoiceId(), getCustomerId(), getPurchaseDate());
    }
}
