package com.trilogyed.retailapiservice.viewmodels;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Objects;

public class OrderViewModel {

    @NotBlank(message = "Customer Id cannot be blank")
    private int customerId;
    private List<ItemViewModel> orderList;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<ItemViewModel> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<ItemViewModel> orderList) {
        this.orderList = orderList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderViewModel that = (OrderViewModel) o;
        return getCustomerId() == that.getCustomerId() &&
                Objects.equals(getOrderList(), that.getOrderList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId(), getOrderList());
    }
}
