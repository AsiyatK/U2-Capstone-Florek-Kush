package com.trilogyed.retailapiservice.viewmodels;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

public class OrderItem {

    @NotNull(message = "productId is a required field")
    @Positive
    private int productId;
    @Positive(message = "quantity must be greater than zero")
    private int quantity;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return getProductId() == orderItem.getProductId() &&
                getQuantity() == orderItem.getQuantity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getQuantity());
    }
}
