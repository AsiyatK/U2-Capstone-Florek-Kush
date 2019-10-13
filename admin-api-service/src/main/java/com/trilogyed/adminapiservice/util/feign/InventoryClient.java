package com.trilogyed.adminapiservice.util.feign;

import com.trilogyed.adminapiservice.viewModel.InventoryViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @RequestMapping(value = "/invetory", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryViewModel createInventory(@RequestBody @Valid InventoryViewModel inventory);

    @RequestMapping(value = "/invetory", method = RequestMethod.GET)
    @ResponseStatus()
}
