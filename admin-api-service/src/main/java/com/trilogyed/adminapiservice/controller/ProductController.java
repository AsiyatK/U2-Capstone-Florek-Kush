package com.trilogyed.adminapiservice.controller;

import com.trilogyed.adminapiservice.exception.NotFoundException;
import com.trilogyed.adminapiservice.service.ServiceLayer;
import com.trilogyed.adminapiservice.viewModel.ProductViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RefreshScope
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ServiceLayer service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductViewModel saveProduct(@RequestBody @Valid ProductViewModel pvm){
        return service.saveProduct(pvm);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductViewModel> findAllProducts(){
        return service.findAllProducts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductViewModel findProduct(@PathVariable("id") int productId){
        ProductViewModel pvm = service.findProduct(productId);
        if(pvm == null){
            throw new NotFoundException("Product with id " + productId + " not found");
        }
        return pvm;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable("id") int productId, @RequestBody @Valid ProductViewModel pvm){
        if(pvm.getProductId() == 0){
            pvm.setProductId(productId);
        }
        if(productId != pvm.getProductId()){
            throw new IllegalArgumentException("Product id on the path must match the id on the Product object");
        }
        service.updateProduct(pvm);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProduct(@PathVariable("id") int productId){
        service.removeProduct(productId);
    }
}
