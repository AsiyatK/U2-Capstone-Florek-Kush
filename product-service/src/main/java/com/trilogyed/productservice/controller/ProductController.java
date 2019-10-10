package com.trilogyed.productservice.controller;


import com.trilogyed.productservice.exception.NotFoundException;
import com.trilogyed.productservice.model.Product;
import com.trilogyed.productservice.model.ProductViewModel;
import com.trilogyed.productservice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ServiceLayer service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductViewModel createCustomer(@RequestBody @Valid Product product){
        return service.saveProduct(product);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductViewModel> getAllProducts(){
        return service.findAllProducts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductViewModel getProduct(@PathVariable("id") int id){
        ProductViewModel productVM = service.findProductById(id);
        if(productVM == null){
            throw new NotFoundException("Product with id  " + id + " not found");
        }
        return productVM;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable("id") int id, @RequestBody @Valid Product product){
        if(product.getProductId() == 0){
            product.setProductId(id);
        }
        if(id != product.getProductId()){
            throw new IllegalArgumentException("Product id on the path must match the id on the Product object");
        }
        service.updateProduct(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("id") int id){
        service.removeProduct(id);
    }
}


































