package com.trilogyed.adminapiservice.controller;


import com.trilogyed.adminapiservice.service.ServiceLayer;
import com.trilogyed.adminapiservice.viewModel.InventoryViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    ServiceLayer service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryViewModel createInventory(@RequestBody @Valid InventoryViewModel inventory) {
        return service.saveInventory(inventory);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryViewModel> getAllInventories(){
        return service.findAllInventories();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryViewModel getInventory(@PathVariable("id") int inventoryId) {
        InventoryViewModel inventory = service.findInventory(inventoryId);

        return inventory;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInventory(@PathVariable("id") int inventoryId, @RequestBody @Valid InventoryViewModel inventory) {
        service.updateInventory(inventoryId, inventory);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable("id") int inventoryId) {
        service.removeInventory(inventoryId);
    }

    @GetMapping(value = "/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryViewModel> getInventoriesByProduct(@PathVariable("id") int productId) {
        return service.findInventoriesByProduct(productId);
    }

    @DeleteMapping(value = "/product/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByProduct(@PathVariable("id") int productId){
        service.removeInventoryByProductId(productId);
    }
}
