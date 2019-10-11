package com.trilogyed.levelupservice.service;

import com.trilogyed.levelupservice.dao.LevelUpDao;
import com.trilogyed.levelupservice.exception.MembershipNotFoundException;
import com.trilogyed.levelupservice.model.LevelUp;
import com.trilogyed.levelupservice.viewmodel.LevelUpViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceLayer {

    private LevelUpDao dao;

    @Autowired
    public ServiceLayer(LevelUpDao dao){
        this.dao = dao;
    }

    public LevelUpViewModel createAccount(LevelUpViewModel newMember){

        LevelUp accountCreated = dao.createNewAccount(newMember);

        newMember.setLevelUpId(accountCreated.getLevelUpId());

        return newMember;

    }

    public LevelUpViewModel getAccount(int levelUpId){

        LevelUp l = dao.getAccount(levelUpId);

        if(l==null){
            throw new MembershipNotFoundException();
        } else{
            LevelUpViewModel account = buildLevelUpViewModel(l);

            if(account != null){
                return account;
            } else {
                throw new MembershipNotFoundException("The account with id " + levelUpId + " could not be located");
            }
        }

    }

    public List<LevelUpViewModel> getAllAccounts(){

        List<LevelUp> accounts = dao.getAllAccounts();

        List<LevelUpViewModel> accountModels = new ArrayList<>();

        accounts.stream()
                .forEach(account -> {
                    LevelUpViewModel model = buildLevelUpViewModel(account);
                    accountModels.add(model);
                });

        return accountModels;

    }

    public LevelUpViewModel getAccountByCustomer(int customerId){

        LevelUp l = dao.getAccountByCustomer(customerId);

        if(l==null){
            throw new MembershipNotFoundException();
        } else{
            LevelUpViewModel account = buildLevelUpViewModel(l);

            if(account != null){
                return account;
            } else {
                throw new MembershipNotFoundException("The account with customerId " + customerId + " could not be located");
            }
        }

    }

    public void updateAccount(LevelUpViewModel updatedAccount){

        dao.updateAccount(updatedAccount);

    }

    public void deleteAccount(int levelUpId){

        dao.deleteAccount(levelUpId);

    }

    public void deleteAccountByCustomer(int customerId){

        dao.deleteAccountByCustomer(customerId);

    }

    private LevelUpViewModel buildLevelUpViewModel(LevelUp account){
        LevelUpViewModel accountModel = new LevelUpViewModel();
        accountModel.setLevelUpId(account.getLevelUpId());
        accountModel.setCustomerId(account.getCustomerId());
        accountModel.setPoints(account.getPoints());
        accountModel.setMemberDate(account.getMemberDate());

        return accountModel;
    }
}
