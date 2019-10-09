package com.trilogyed.levelupservice.dao;


import com.trilogyed.levelupservice.model.LevelUp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LevelUpDaoTest {

    @Autowired
    LevelUpDao levelUpDao;

    @Before
    public void setUp(){
        List<LevelUp> memberList = levelUpDao.getAllAccounts();
        memberList.stream()
                .forEach(account -> levelUpDao.deleteAccount(account.getLevelUpId()));
    }

    @After
    public void tearDown(){
        List<LevelUp> memberList = levelUpDao.getAllAccounts();
        memberList.stream()
                .forEach(account -> levelUpDao.deleteAccount(account.getLevelUpId()));
    }

    @Test
    public void addGetGetByCustomerDeleteAccount(){
        //add, get, delete
        LevelUp account = new LevelUp();
        account.setCustomerId(732);
        account.setPoints(5000);
        account.setMemberDate(LocalDate.of(2010, 8, 2));

        account = levelUpDao.createNewAccount(account);

        LevelUp accountFromDb = levelUpDao.getAccount(account.getLevelUpId());

        assertEquals(account, accountFromDb);

        LevelUp accountByCustomer = levelUpDao.getAccountByCustomer(account.getCustomerId());

        assertEquals(account, accountByCustomer);

        levelUpDao.deleteAccount(account.getLevelUpId());

        account = levelUpDao.getAccount(account.getLevelUpId());

        assertNull(account);

    }

    @Test
    public void updateAccount(){

        LevelUp account = new LevelUp();
        account.setCustomerId(732);
        account.setPoints(5000);
        account.setMemberDate(LocalDate.of(2010, 8, 2));

        account = levelUpDao.createNewAccount(account);

        //update in local memory
        account.setPoints(99999);

        //set new instance equal to db version
        LevelUp accountFromDb = levelUpDao.getAccount(account.getLevelUpId());
        //test that initial console added to db is not same as local memory
        assertNotEquals(account, accountFromDb);
        //update console in db
        levelUpDao.updateAccount(account);
        //set c1 equal to updated console from db
        accountFromDb = levelUpDao.getAccount(account.getLevelUpId());
        //test that updated console from db and console in local are now equal
        assertEquals(account, accountFromDb);

    }

    @Test
    public void getAllAccounts(){

        List<LevelUp> accountList = levelUpDao.getAllAccounts();

        assertEquals(accountList.size(), 0);

        LevelUp account = new LevelUp();
        account.setCustomerId(732);
        account.setPoints(5000);
        account.setMemberDate(LocalDate.of(2010, 8, 2));

        levelUpDao.createNewAccount(account);

        accountList = levelUpDao.getAllAccounts();

        assertEquals(accountList.size(), 1);

        account = new LevelUp();
        account.setCustomerId(908);
        account.setPoints(1000);
        account.setMemberDate(LocalDate.of(2012, 7, 19));

        levelUpDao.createNewAccount(account);

        accountList = levelUpDao.getAllAccounts();

        assertEquals(accountList.size(), 2);

    }

}
