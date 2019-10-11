package com.trilogyed.inventoryservice.controller;

import com.trilogyed.inventoryservice.service.ServiceLayer;
import com.trilogyed.inventoryservice.viewmodels.InventoryViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value = "/inventory")
public class InventoryController {

    @Autowired
    ServiceLayer sl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryViewModel createInventory(@RequestBody @Valid InventoryViewModel inventory) {
        return sl.createInventory(inventory);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryViewModel getInventory(@PathVariable("id") int inventoryId) {
        InventoryViewModel inventory = sl.getInventory(inventoryId);

        return inventory;
    }

    @GetMapping(value = "/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryViewModel> getInventoriesByProduct(@PathVariable("id") int productId) {
        return sl.getInventoriesByProduct(productId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryViewModel> getAllInventories(){
        return sl.getAllInventories();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInventory(@PathVariable("id") int inventoryId, @RequestBody @Valid InventoryViewModel inventory) {
        sl.updateInventory(inventory);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable("id") int inventoryId) {
        sl.deleteInventory(inventoryId);
    }

    @DeleteMapping(value = "/product/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByCustomer(@PathVariable("id") int productId){
        sl.deleteInventoriesByProduct(productId);
    }
}
