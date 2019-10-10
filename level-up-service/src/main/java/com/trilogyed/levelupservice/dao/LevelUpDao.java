package com.trilogyed.levelupservice.dao;

import com.trilogyed.levelupservice.model.LevelUp;

import java.util.List;

public interface LevelUpDao {

    LevelUp createNewAccount(LevelUp newAccount);

    LevelUp getAccount(int levelUpId);

    LevelUp getAccountByCustomer(int customerId);

    List<LevelUp> getAllAccounts();

    void updateAccount(LevelUp updatedAccount);

    void deleteAccount(int levelUpId);

    void deleteAccountByCustomer(int customerId);
}
