package com.trilogyed.adminapiservice.controller;


import com.trilogyed.adminapiservice.service.ServiceLayer;
import com.trilogyed.adminapiservice.viewModel.LevelUpViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value = "/level-up")
public class LevelUpController {
    @Autowired
    ServiceLayer service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LevelUpViewModel createAccount(@RequestBody @Valid LevelUpViewModel lvm){
        return service.saveAccount(lvm);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LevelUpViewModel> getAllAccounts(){
        return service.findAllAccounts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUpViewModel getAccount(@PathVariable("id") int accountId){
        return service.findAcount(accountId);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAccount(@PathVariable("id") int levelUpId, @RequestBody @Valid LevelUpViewModel account) {
        service.updateAccount(levelUpId, account);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("id") int levelUpId) {

        service.deleteAccount(levelUpId);
    }

    @GetMapping(value = "/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUpViewModel getAccountByCustomer(@PathVariable("customerId") int customerId) {
        return service.findAccountByCustomer(customerId);
    }

    @DeleteMapping(value = "/customer/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByCustomer(@PathVariable("customerId") int customerId){

        service.deleteByCustomer(customerId);
    }


}
