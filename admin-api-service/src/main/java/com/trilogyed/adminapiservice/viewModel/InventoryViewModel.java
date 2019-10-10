package com.trilogyed.adminapiservice.viewModel;

import java.util.Objects;

public class InventoryViewModel {

    private int inventoryId;
    private int productId;
    private int quantity;

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

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
        InventoryViewModel that = (InventoryViewModel) o;
        return getInventoryId() == that.getInventoryId() &&
                getProductId() == that.getProductId() &&
                getQuantity() == that.getQuantity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInventoryId(), getProductId(), getQuantity());
    }

    @Override
    public String toString() {
        return "InventoryViewModel{" +
                "inventoryId=" + inventoryId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
