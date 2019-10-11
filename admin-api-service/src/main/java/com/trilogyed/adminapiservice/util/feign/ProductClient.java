package com.trilogyed.adminapiservice.util.feign;


import com.trilogyed.adminapiservice.model.Product;
import com.trilogyed.adminapiservice.viewModel.ProductViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    ProductViewModel createProduct(@RequestBody @Valid Product product);

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<ProductViewModel> getAllProducts();

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    ProductViewModel getProduct(@PathVariable("id") int id);

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateProduct(@PathVariable("id") int id, @RequestBody @Valid Product product);

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProduct(@PathVariable("id") int id);
}
