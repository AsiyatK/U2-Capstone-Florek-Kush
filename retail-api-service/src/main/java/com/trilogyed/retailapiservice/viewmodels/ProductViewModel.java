package com.trilogyed.retailapiservice.viewmodels;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductViewModel {

    private int productId;
    private String productName;
    private String productDescription;
    private BigDecimal listPrice;
    private int quantityInStock;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

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

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductViewModel that = (ProductViewModel) o;
        return getProductId() == that.getProductId() &&
                getQuantityInStock() == that.getQuantityInStock() &&
                Objects.equals(getProductName(), that.getProductName()) &&
                Objects.equals(getProductDescription(), that.getProductDescription()) &&
                Objects.equals(getListPrice(), that.getListPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getProductName(), getProductDescription(), getListPrice(), getQuantityInStock());
    }

    @Override
    public String toString() {
        return "ProductViewModel{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", listPrice=" + listPrice +
                ", quantityInStock=" + quantityInStock +
                '}';
    }
}
