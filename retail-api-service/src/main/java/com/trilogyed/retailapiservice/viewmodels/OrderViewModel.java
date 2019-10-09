package com.trilogyed.retailapiservice.viewmodels;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Objects;

public class OrderViewModel {

    @NotBlank(message = "Order quantity cannot be blank")
    @Positive(message = "Order quantity must be greater than zero")
    private int orderQuantity;
    @NotBlank(message = "Product id cannot be blank")
    private int productId;
    @NotBlank(message = "Customer Id cannot be blank")
    private int customerId;

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderViewModel that = (OrderViewModel) o;
        return getOrderQuantity() == that.getOrderQuantity() &&
                getProductId() == that.getProductId() &&
                getCustomerId() == that.getCustomerId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderQuantity(), getProductId(), getCustomerId());
    }


}
