package com.trilogyed.inventoryservice.service;


import com.trilogyed.inventoryservice.dao.InventoryDao;
import com.trilogyed.inventoryservice.dao.InventoryDaoJdbcTemplateImpl;
import com.trilogyed.inventoryservice.models.Inventory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnit4.class)
public class ServiceLayerTest {

    ServiceLayer sl;
    InventoryDao inventoryDao;

    @Before
    public void setUp() throws Exception {
        setUpInventoryMock();

        sl = new ServiceLayer(inventoryDao);
    }

    @Test
    public void createGetInventoryGetInventoryByProduct(){

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
    public void getAllInventories(){
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
    public void updateDeleteAccount(){
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

    private void setUpInventoryMock(){
        inventoryDao = mock(InventoryDaoJdbcTemplateImpl.class);

        Inventory inventoryAdded = new Inventory();
        inventoryAdded.setInventoryId(732);
        inventoryAdded.setProductId(732);
        inventoryAdded.setQuantity(82);

        Inventory inventoryNew = new Inventory();
        inventoryNew.setProductId(732);
        inventoryNew.setQuantity(82);

        doReturn(inventoryAdded).when(inventoryDao).addProductToInventory(inventoryNew);
        doReturn(inventoryAdded).when(inventoryDao).getInventory(732);

        List<Inventory> inventoriesByProduct = new ArrayList<>();
        inventoriesByProduct.add(inventoryAdded);

        doReturn(inventoryAdded).when(inventoryDao).getInventoriesByProduct(732);

        Inventory inventoryUpdated = new Inventory();
        inventoryUpdated.setInventoryId(908);
        inventoryUpdated.setProductId(908);
        inventoryUpdated.setQuantity(87);

        Inventory inventoryDeleted = new Inventory();
        inventoryDeleted.setInventoryId(973);
        inventoryDeleted.setProductId(973);
        inventoryDeleted.setQuantity(37);

        List<Inventory> allInventories = new ArrayList<>();
        allInventories.add(inventoryAdded);
        allInventories.add(inventoryUpdated);
        allInventories.add(inventoryDeleted);

        //mock response for getting all inventories
        doReturn(allInventories).when(inventoryDao).getAllInventories();

        //mock response for updating inventory
        doNothing().when(inventoryDao).updateInventory(inventoryUpdated);
        doReturn(inventoryUpdated).when(inventoryDao).getInventory(908);

        //mock response for deleting inventory
        doNothing().when(inventoryDao).deleteInventory(973);
        doReturn(null).when(inventoryDao).getInventory(973);

    }
}
