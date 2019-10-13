package com.trilogyed.retailapiservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.trilogyed.retailapiservice.util.feign.InventoryServiceClient;
import com.trilogyed.retailapiservice.util.feign.LevelUpServiceClient;
import com.trilogyed.retailapiservice.util.feign.ProductServiceClient;
import com.trilogyed.retailapiservice.viewmodels.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceLayer {

    LevelUpServiceClient levelUpClient;
    ProductServiceClient productClient;
    InventoryServiceClient inventoryClient;

    public ServiceLayer(LevelUpServiceClient levelUpClient,
                        ProductServiceClient productClient,
                        InventoryServiceClient inventoryClient)
    {
        this.levelUpClient=levelUpClient;
        this.productClient=productClient;
        this.inventoryClient=inventoryClient;
    }

    public ProductViewModel getProduct(int productId){
        ProductViewModel product = productClient.getProduct(productId);

        List<InventoryViewModel> productInventories = inventoryClient.getInventoriesByProduct(productId);
        //sum of all inventory quantities for the product
        int totalInStock = productInventories.stream().mapToInt(InventoryViewModel::getQuantity).sum();
        //set sum equal to quantityInStock property
        product.setQuantityInStock(totalInStock);
        return product;

    }

    public List<ProductViewModel> getAllProducts(){
        List<ProductViewModel> allProducts = productClient.getAllProducts();

        List<InventoryViewModel> allInventories = inventoryClient.getAllInventories();

        for (int i = 0; i < allProducts.size(); i++) {

            int currentProductId = allProducts.get(i).getProductId();

            int totalInStock = allInventories.stream()
                    .filter(inventory -> inventory.getProductId() == currentProductId)
                    .mapToInt(InventoryViewModel::getQuantity).sum();

            allProducts.get(i).setQuantityInStock(totalInStock);
        }

        return allProducts;
    }

    //where the magic happens
    public PurchaseViewModel generateInvoice(OrderViewModel order){

        return null;
    }

    @HystrixCommand(fallbackMethod = "defaultRewards")
    public LevelUpViewModel getRewardsData(int customerId){

        LevelUpViewModel account = levelUpClient.getAccountByCustomer(customerId);

        return account;

    }

    //This should be incorporated as part of the invoice creation method
    public void updateRewardsPoints(int customerId, LevelUpViewModel update){
        levelUpClient.updateAccount(customerId, update);
    }

    //circuit breaker fallback method
    private LevelUpViewModel defaultRewards(int customerId){
        LevelUpViewModel lvm = new LevelUpViewModel();
        return lvm;
    }

}
