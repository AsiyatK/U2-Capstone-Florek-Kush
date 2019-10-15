package com.trilogyed.levelupqueueconsumer.util.feign;

import com.trilogyed.levelupqueueconsumer.viewmodels.LevelUpViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Service
@FeignClient(name = "level-up-service")
@RequestMapping(value="/level-up", produces = "application/json")
public interface LevelUpServiceClient {

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateAccount(@PathVariable("id") int levelUpId, @RequestBody @Valid LevelUpViewModel account);

}
