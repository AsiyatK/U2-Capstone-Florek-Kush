package com.trilogyed.retailapiservice.util.feign;

import com.trilogyed.retailapiservice.viewmodels.ProductViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
@FeignClient(name = "product-service")
@RequestMapping(value="/products", produces = "application/json")
public interface ProductServiceClient {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ProductViewModel getProduct(@PathVariable("id") int id);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<ProductViewModel> getAllProducts();

}
