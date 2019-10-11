package com.trilogyed.adminapiservice.util.feign;

import com.trilogyed.adminapiservice.viewModel.LevelUpViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "level-up-service")
public interface LevelUpClient {

    @RequestMapping(value = "/level-up", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    LevelUpViewModel createAccount(@RequestBody @Valid LevelUpViewModel account);

    @RequestMapping(value = "level-up/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    LevelUpViewModel getAccount(@PathVariable("id") int levelUpId);

    @GetMapping(value = "level-up/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    LevelUpViewModel getAccountByCustomer(@PathVariable("customerId") int customerId);

    @RequestMapping(value = "/level-up", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<LevelUpViewModel> getAllAccounts();

    @RequestMapping(value = "level-up/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateAccount(@PathVariable("id") int levelUpId, @RequestBody @Valid LevelUpViewModel account);

    @RequestMapping(value = "level-up/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAccount(@PathVariable("id") int levelUpId);

    @RequestMapping(value = "level-up/customer/{customerId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteByCustomer(@PathVariable("customerId") int customerId);
}
