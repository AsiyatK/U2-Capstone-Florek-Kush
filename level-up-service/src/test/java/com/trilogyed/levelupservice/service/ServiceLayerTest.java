package com.trilogyed.levelupservice.service;


import com.trilogyed.levelupservice.dao.LevelUpDao;
import com.trilogyed.levelupservice.dao.LevelUpDaoJdbcTemplateImpl;
import com.trilogyed.levelupservice.exception.MembershipNotFoundException;
import com.trilogyed.levelupservice.model.LevelUp;
import com.trilogyed.levelupservice.viewmodel.LevelUpViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnit4.class)
public class ServiceLayerTest {


    ServiceLayer sl;
    LevelUpDao levelUpDao;

    @Before
    public void setUp() throws Exception {
        setUpLevelUpMock();

        sl = new ServiceLayer(levelUpDao);
    }

    @Test
    public void createGetAccountGetAccountByCustomer(){

        LevelUpViewModel accountToAdd = new LevelUpViewModel();
        accountToAdd.setCustomerId(732);
        accountToAdd.setPoints(5000);
        accountToAdd.setMemberDate(LocalDate.of(2010, 8, 2));

        accountToAdd = sl.createAccount(accountToAdd);

        LevelUpViewModel accountCreated = sl.getAccount(accountToAdd.getLevelUpId());

        assertEquals(accountToAdd, accountCreated);

        LevelUpViewModel accountByCustomer = sl.getAccountByCustomer(accountToAdd.getCustomerId());

        assertEquals(accountToAdd, accountByCustomer);
    }

    @Test
    public void getAllAccounts(){
        List<LevelUpViewModel> allAccounts = new ArrayList<>();

        LevelUpViewModel account1 = new LevelUpViewModel();
        account1.setLevelUpId(732);
        account1.setCustomerId(732);
        account1.setPoints(5000);
        account1.setMemberDate(LocalDate.of(2010, 8, 2));

        LevelUpViewModel account2 = new LevelUpViewModel();
        account2.setLevelUpId(908);
        account2.setCustomerId(908);
        account2.setPoints(2000);
        account2.setMemberDate(LocalDate.of(2012, 7, 19));

        LevelUpViewModel account3 = new LevelUpViewModel();
        account3.setLevelUpId(973);
        account3.setCustomerId(973);
        account3.setPoints(3000);
        account3.setMemberDate(LocalDate.of(2019, 6, 25));

        allAccounts.add(account1);
        allAccounts.add(account2);
        allAccounts.add(account3);

        List<LevelUpViewModel> accountsFromService = sl.getAllAccounts();

        assertEquals(accountsFromService.size(), allAccounts.size());

        assertEquals(allAccounts, accountsFromService);
    }

    @Test
    public void updateAccount(){
        LevelUpViewModel accountUpdate = new LevelUpViewModel();
        accountUpdate.setLevelUpId(908);
        accountUpdate.setCustomerId(908);
        accountUpdate.setPoints(1000);
        accountUpdate.setMemberDate(LocalDate.of(2012, 7, 19));

        accountUpdate.setPoints(2000);

        sl.updateAccount(accountUpdate);

        LevelUpViewModel accountFromService = sl.getAccount(accountUpdate.getLevelUpId());

        assertEquals(accountUpdate, accountFromService);

    }

    @Test(expected = MembershipNotFoundException.class)
    public void deleteAccount(){
        LevelUpViewModel accountDelete = new LevelUpViewModel();
        accountDelete.setLevelUpId(973);

        sl.deleteAccount(accountDelete.getLevelUpId());

        accountDelete = sl.getAccount(accountDelete.getLevelUpId());

        fail("Should throw an error because account does not exist");
    }

    @Test(expected = MembershipNotFoundException.class)
    public void deleteAccountByCustomer(){
        LevelUpViewModel accountDelete = new LevelUpViewModel();
        accountDelete.setCustomerId(973);

        sl.deleteAccountByCustomer(accountDelete.getCustomerId());

        accountDelete = sl.getAccountByCustomer(accountDelete.getCustomerId());

        fail("Should throw an error because account does not exist");
    }

    private void setUpLevelUpMock(){
        levelUpDao = mock(LevelUpDaoJdbcTemplateImpl.class);

        LevelUp accountAdded = new LevelUp();
        accountAdded.setLevelUpId(732);
        accountAdded.setCustomerId(732);
        accountAdded.setPoints(5000);
        accountAdded.setMemberDate(LocalDate.of(2010, 8, 2));

        LevelUpViewModel accountNew = new LevelUpViewModel();
        accountNew.setCustomerId(732);
        accountNew.setPoints(5000);
        accountNew.setMemberDate(LocalDate.of(2010, 8, 2));

        doReturn(accountAdded).when(levelUpDao).createNewAccount(accountNew);
        doReturn(accountAdded).when(levelUpDao).getAccount(732);
        doReturn(accountAdded).when(levelUpDao).getAccountByCustomer(732);

        LevelUpViewModel accountUpdated = new LevelUpViewModel();
        accountUpdated.setLevelUpId(908);
        accountUpdated.setCustomerId(908);
        accountUpdated.setPoints(2000);
        accountUpdated.setMemberDate(LocalDate.of(2012, 7, 19));

        LevelUpViewModel accountDeleted = new LevelUpViewModel();
        accountDeleted.setLevelUpId(973);
        accountDeleted.setCustomerId(973);
        accountDeleted.setPoints(3000);
        accountDeleted.setMemberDate(LocalDate.of(2019, 6, 25));

        //mock setUp for get all accounts
        List<LevelUp> allAccounts = new ArrayList<>();
        allAccounts.add(accountAdded);
        allAccounts.add(accountUpdated);
        allAccounts.add(accountDeleted);

        //mock response for getting all accounts
        doReturn(allAccounts).when(levelUpDao).getAllAccounts();

        //mock response for updating account
        doNothing().when(levelUpDao).updateAccount(accountUpdated);
        doReturn(accountUpdated).when(levelUpDao).getAccount(908);

        //mock response for deleting account
        doNothing().when(levelUpDao).deleteAccount(973);
        doReturn(null).when(levelUpDao).getAccount(973);
        doReturn(null).when(levelUpDao).getAccountByCustomer(973);
    }

}
