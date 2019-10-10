package com.trilogyed.productservice.dao;

import com.trilogyed.productservice.model.Product;

import java.util.List;

public interface ProductDao {

    //create
    Product addProduct(Product product);
    //read
    Product getProduct(int productId);
    //read all
    List<Product> getAllProducts();
    //update
    void updateProduct(Product product);
    //delete
    void deleteProduct(int productId);
}
