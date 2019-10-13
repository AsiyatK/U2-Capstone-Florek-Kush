package com.trilogyed.retailapiservice.viewmodels;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class InvoiceViewModel {

    private int invoiceId;
    @NotNull(message = "customerId is a required field")
    private int customerId;
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
        InvoiceViewModel that = (InvoiceViewModel) o;
        return getInvoiceId() == that.getInvoiceId() &&
                getCustomerId() == that.getCustomerId() &&
                Objects.equals(getPurchaseDate(), that.getPurchaseDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInvoiceId(), getCustomerId(), getPurchaseDate());
    }
}
