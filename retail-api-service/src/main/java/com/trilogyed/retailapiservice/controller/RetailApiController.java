package com.trilogyed.retailapiservice.controller;

import com.trilogyed.retailapiservice.models.LevelUp;
import com.trilogyed.retailapiservice.service.ServiceLayer;
import com.trilogyed.retailapiservice.viewmodels.InvoiceViewModel;
import com.trilogyed.retailapiservice.viewmodels.LevelUpViewModel;
import com.trilogyed.retailapiservice.viewmodels.OrderViewModel;
import com.trilogyed.retailapiservice.viewmodels.ProductViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value = "/retail")
public class RetailApiController {

    @Autowired
    ServiceLayer sl;

    @GetMapping(value = "/products/{productId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ProductViewModel viewProduct(@PathVariable(name = "productId") int productId){

        //sl.getProductById(productId);

        ProductViewModel pvm = new ProductViewModel();

        return pvm;
    }

    @GetMapping(value = "/products/inventory")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProductViewModel> browseProductsInStock(){

        //sl.getProductsByInventory();

        List<ProductViewModel> productList = new ArrayList<>();

        return productList;
    }

    @GetMapping(value = "/products/invoice/{invoiceId}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProductViewModel> viewProductDetails(@PathVariable(name = "invoiceId") int invoiceId){

        //sl.getProductsByInvoice(invoiceId);

        List<ProductViewModel> productList = new ArrayList<>();

        return productList;
    }

    @GetMapping(value = "/products")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProductViewModel> browseAllProducts(){

        //sl.getAllProducts();

        List<ProductViewModel> productList = new ArrayList<>();

        return productList;
    }

    @PostMapping(value = "/invoices")
    @ResponseStatus(value = HttpStatus.CREATED)
    public InvoiceViewModel placeOrder(@RequestBody OrderViewModel order){

        //sl.createInvoice(order);

        InvoiceViewModel ivm = new InvoiceViewModel();

        return ivm;
    }

    @GetMapping(value = "/invoices/{invoiceId}")
    @ResponseStatus(value = HttpStatus.OK)
    public InvoiceViewModel viewInvoice(@PathVariable(name = "invoiceId") int invoiceId){

        //sl.getInvoiceById(customerId);

        InvoiceViewModel invoice = new InvoiceViewModel();

        return invoice;
    }

    @GetMapping(value = "/invoices/customer/{customerId}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<InvoiceViewModel> viewPurchaseHistory(@PathVariable(name = "customerId") int customerId){

        //sl.getInvoicesByCustomer(customerId);

        List<InvoiceViewModel> invoiceList = new ArrayList<>();

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
    public void addRewards(@PathVariable(name = "customerId") int customerId){

        //sl.updateRewardsData(customerId);

    }

}
