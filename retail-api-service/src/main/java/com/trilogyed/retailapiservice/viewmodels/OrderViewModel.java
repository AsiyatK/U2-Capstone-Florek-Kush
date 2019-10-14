package com.trilogyed.retailapiservice.viewmodels;


import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

public class OrderViewModel {

    @NotBlank(message = "Customer Id cannot be blank")
    private int customerId;
    private List<OrderItem> orderList;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<OrderItem> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderItem> orderList) {
        this.orderList = orderList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderViewModel that = (OrderViewModel) o;
        return getCustomerId() == that.getCustomerId() &&
                getOrderList().equals(that.getOrderList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId(), getOrderList());
    }
}
