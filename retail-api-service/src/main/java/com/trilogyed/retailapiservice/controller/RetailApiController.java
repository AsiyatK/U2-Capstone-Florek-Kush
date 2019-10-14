package com.trilogyed.retailapiservice.controller;

import com.trilogyed.retailapiservice.service.ServiceLayer;
import com.trilogyed.retailapiservice.viewmodels.PurchaseViewModel;
import com.trilogyed.retailapiservice.viewmodels.backing.LevelUpViewModel;
import com.trilogyed.retailapiservice.viewmodels.OrderViewModel;
import com.trilogyed.retailapiservice.viewmodels.backing.ProductViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value = "/retail", produces = "application/json")
public class RetailApiController {

    @Autowired
    ServiceLayer sl;

    @GetMapping(value = "/products/{productId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ProductViewModel viewProduct(@PathVariable(name = "productId") int productId){

        ProductViewModel product = sl.getProduct(productId);

        return product;

    }

    @GetMapping(value = "/products")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProductViewModel> browseAllProducts(){

        List<ProductViewModel> productList = sl.getAllProducts();

        return productList;
    }

    @PostMapping(value = "/invoices")
    @ResponseStatus(value = HttpStatus.CREATED)
    public PurchaseViewModel placeOrder(@RequestBody OrderViewModel order){

        //sl.createInvoice(order);

        PurchaseViewModel ivm = new PurchaseViewModel();

        return ivm;
    }

    @GetMapping(value = "/invoices/{invoiceId}")
    @ResponseStatus(value = HttpStatus.OK)
    public PurchaseViewModel viewInvoice(@PathVariable(name = "invoiceId") int invoiceId){

        //sl.getInvoiceById(customerId);

        PurchaseViewModel invoice = new PurchaseViewModel();

        return invoice;
    }

    @GetMapping(value = "/invoices/customer/{customerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<PurchaseViewModel> viewPurchaseHistory(@PathVariable(name = "customerId") int customerId){

        //sl.getInvoicesByCustomer(customerId);

        List<PurchaseViewModel> invoiceList = new ArrayList<>();

        return invoiceList;
    }

    //requests will be processed through Circuit Breaker
    @GetMapping(value = "/level-up/{customerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public LevelUpViewModel viewRewards(@PathVariable(name = "customerId") int customerId){

        return sl.getRewardsData(customerId);

    }

    //requests will be processed through rabbitMQ
    @PutMapping(value = "/level-up/{customerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void addRewardsPoints(@PathVariable(name = "customerId") int customerId,
                                 @RequestBody LevelUpViewModel update){
        sl.updateRewardsPoints(customerId, update);

    }

}
