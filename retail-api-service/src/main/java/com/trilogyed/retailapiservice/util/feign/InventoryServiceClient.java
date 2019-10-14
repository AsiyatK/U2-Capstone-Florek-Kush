package com.trilogyed.retailapiservice.util.feign;

import com.trilogyed.retailapiservice.viewmodels.backing.InventoryViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Service
@FeignClient(name = "inventory-service")
@RequestMapping(value="/inventory", produces = "application/json")
public interface InventoryServiceClient {

    @GetMapping(value = "/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryViewModel> getInventoriesByProduct(@PathVariable("id") int productId);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryViewModel> getAllInventories();

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInventory(@PathVariable("id") int inventoryId, @RequestBody @Valid InventoryViewModel inventory);
}
