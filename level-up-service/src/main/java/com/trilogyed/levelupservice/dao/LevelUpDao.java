package com.trilogyed.levelupservice.dao;

import com.trilogyed.levelupservice.model.LevelUp;

import java.util.List;

public interface LevelUpDao {

    public LevelUp createNewAccount(LevelUp newAccount);

    public LevelUp getAccount(int levelUpId);

    public LevelUp getAccountByCustomer(int customerId);

    public List<LevelUp> getAllAccounts();

    public void updateAccount(LevelUp updatedAccount);

    public void deleteAccount(int levelUpId);
}
