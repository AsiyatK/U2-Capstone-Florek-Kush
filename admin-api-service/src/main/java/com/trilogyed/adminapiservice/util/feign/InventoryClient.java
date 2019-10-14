package com.trilogyed.adminapiservice.util.feign;

import com.trilogyed.adminapiservice.viewModel.InventoryViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @RequestMapping(value = "/invetory", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    InventoryViewModel createInventory(@RequestBody @Valid InventoryViewModel inventory);

    @RequestMapping(value = "/inventory", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<InventoryViewModel> getAllInventories();

    @RequestMapping(value = "inventory/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    InventoryViewModel getInventory(@PathVariable("id") int inventoryId);

    @RequestMapping(value = "inventory/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    List<InventoryViewModel> getInventoriesByProduct(@PathVariable("id") int productId);

    @RequestMapping(value = "inventory/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateInventory(@PathVariable("id") int inventoryId, @RequestBody @Valid InventoryViewModel inventory);

    @RequestMapping(value = "inventory/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteInventory(@PathVariable("id") int inventoryId);

    @RequestMapping(value = "inventory/product/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteByProduct(@PathVariable("id") int productId);

}
