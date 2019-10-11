package com.trilogyed.adminapiservice.util.feign;

import com.trilogyed.adminapiservice.viewModel.LevelUpViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "level-up-service")
public interface LevelUpClient {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    LevelUpViewModel createAccount(@RequestBody @Valid LevelUpViewModel account);

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    LevelUpViewModel getAccount(@PathVariable("id") int levelUpId);

    @GetMapping(value = "/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    LevelUpViewModel getAccountByCustomer(@PathVariable("customerId") int customerId);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<LevelUpViewModel> getAllAccounts();

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateAccount(@PathVariable("id") int levelUpId, @RequestBody @Valid LevelUpViewModel account);

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteComment(@PathVariable("id") int levelUpId);

    @DeleteMapping(value = "/customer/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteByPost(@PathVariable("customerId") int customerId);
}
