package com.trilogyed.levelupservice.controller;

import com.trilogyed.levelupservice.model.LevelUp;
import com.trilogyed.levelupservice.service.ServiceLayer;
import com.trilogyed.levelupservice.viewmodel.LevelUpViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping(value = "/level-up", produces = "application/json")
public class LevelUpController {

    @Autowired
    ServiceLayer sl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LevelUpViewModel createAccount(@RequestBody @Valid LevelUpViewModel account) {
        return sl.createAccount(account);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUpViewModel getAccount(@PathVariable("id") int levelUpId) {
        LevelUpViewModel account = sl.getAccount(levelUpId);

        return account;
    }

    @GetMapping(value = "/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUpViewModel getAccountByCustomer(@PathVariable("customerId") int customerId) {
        return sl.getAccountByCustomer(customerId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LevelUpViewModel> getAllAccounts(){
        return sl.getAllAccounts();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAccount(@PathVariable("id") int levelUpId, @RequestBody @Valid LevelUpViewModel account) {
        sl.updateAccount(account);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("id") int levelUpId) {
        sl.deleteAccount(levelUpId);
    }

    @DeleteMapping(value = "/customer/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByCustomer(@PathVariable("customerId") int customerId){
        sl.deleteAccountByCustomer(customerId);
    }

}
