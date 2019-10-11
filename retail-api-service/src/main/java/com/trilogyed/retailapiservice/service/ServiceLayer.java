package com.trilogyed.retailapiservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.trilogyed.retailapiservice.util.feign.LevelUpServiceClient;
import com.trilogyed.retailapiservice.viewmodels.LevelUpViewModel;
import org.springframework.stereotype.Service;
import sun.tools.jconsole.LocalVirtualMachine;

@Service
public class ServiceLayer {

    LevelUpServiceClient levelUpClient;

    public ServiceLayer(LevelUpServiceClient levelUpClient){
        this.levelUpClient=levelUpClient;
    }

    @HystrixCommand(fallbackMethod = "defaultRewards")
    public LevelUpViewModel getRewardsData(int customerId){

        LevelUpViewModel account = levelUpClient.getAccountByCustomer(customerId);

        return account;

    }

    private LevelUpViewModel defaultRewards(int customerId){
        LevelUpViewModel lvm = new LevelUpViewModel();
        return lvm;
    }

}
