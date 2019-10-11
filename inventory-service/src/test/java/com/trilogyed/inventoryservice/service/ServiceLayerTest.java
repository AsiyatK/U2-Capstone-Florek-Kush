package com.trilogyed.inventoryservice.service;


import com.trilogyed.inventoryservice.dao.InventoryDao;
import com.trilogyed.inventoryservice.dao.InventoryDaoJdbcTemplateImpl;
import com.trilogyed.inventoryservice.exceptions.InventoryNotFoundException;
import com.trilogyed.inventoryservice.models.Inventory;
import com.trilogyed.inventoryservice.viewmodels.InventoryViewModel;
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

        InventoryViewModel inventoryToAdd = new InventoryViewModel();
        inventoryToAdd.setProductId(732);
        inventoryToAdd.setQuantity(82);

        inventoryToAdd = sl.createInventory(inventoryToAdd);

        InventoryViewModel inventoryCreated = sl.getInventory(inventoryToAdd.getInventoryId());

        assertEquals(inventoryToAdd, inventoryCreated);

        List<InventoryViewModel> inventoryList = new ArrayList<>();
        inventoryList.add(inventoryToAdd);

        List<InventoryViewModel> inventoryByProduct = sl.getInventoriesByProduct(inventoryToAdd.getProductId());

        assertEquals(inventoryList, inventoryByProduct);
        assertEquals(1, inventoryByProduct.size());
    }

    @Test
    public void getAllInventories(){
        List<InventoryViewModel> allInventories = new ArrayList<>();

        InventoryViewModel inventory = new InventoryViewModel();
        inventory.setInventoryId(732);
        inventory.setProductId(732);
        inventory.setQuantity(82);

        allInventories.add(inventory);

        inventory = new InventoryViewModel();
        inventory.setInventoryId(908);
        inventory.setProductId(908);
        inventory.setQuantity(87);

        allInventories.add(inventory);

        inventory = new InventoryViewModel();
        inventory.setInventoryId(973);
        inventory.setProductId(973);
        inventory.setQuantity(37);

        allInventories.add(inventory);

        List<InventoryViewModel> inventoriesFromService = sl.getAllInventories();

        assertEquals(allInventories.size(), inventoriesFromService.size());

        assertEquals(allInventories, inventoriesFromService);
    }

    @Test
    public void updateInventory(){
        InventoryViewModel inventoryUpdate = new InventoryViewModel();
        inventoryUpdate.setInventoryId(908);
        inventoryUpdate.setProductId(908);
        inventoryUpdate.setQuantity(1);

        inventoryUpdate.setQuantity(87);

        sl.updateInventory(inventoryUpdate);

        InventoryViewModel inventoryFromService = sl.getInventory(inventoryUpdate.getInventoryId());

        assertEquals(inventoryUpdate, inventoryFromService);

    }

    @Test(expected = InventoryNotFoundException.class)
    public void deleteInventory(){
        InventoryViewModel inventoryDelete = new InventoryViewModel();
        inventoryDelete.setInventoryId(973);

        sl.deleteInventory(inventoryDelete.getInventoryId());

        inventoryDelete = sl.getInventory(inventoryDelete.getInventoryId());

        fail("Should throw an error because inventory does not exist");
    }

    @Test
    public void deleteInventoryByProduct(){
        InventoryViewModel inventoryDelete = new InventoryViewModel();
        inventoryDelete.setProductId(973);

        sl.deleteInventoriesByProduct(inventoryDelete.getInventoryId());


        List<InventoryViewModel> inventoriesByProduct = sl.getInventoriesByProduct(inventoryDelete.getProductId());

        assertEquals(0, inventoriesByProduct.size());
    }

    private void setUpInventoryMock(){
        inventoryDao = mock(InventoryDaoJdbcTemplateImpl.class);

        Inventory inventoryAdded = new Inventory();
        inventoryAdded.setInventoryId(732);
        inventoryAdded.setProductId(732);
        inventoryAdded.setQuantity(82);

//Is this going to create issues when actually sending data to service layer?
        InventoryViewModel inventoryNew = new InventoryViewModel();
        inventoryNew.setProductId(732);
        inventoryNew.setQuantity(82);

        doReturn(inventoryAdded).when(inventoryDao).addProductToInventory(inventoryNew);
        doReturn(inventoryAdded).when(inventoryDao).getInventory(732);

        List<Inventory> inventoriesByProduct = new ArrayList<>();
        inventoriesByProduct.add(inventoryAdded);

        doReturn(inventoriesByProduct).when(inventoryDao).getInventoriesByProduct(732);

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

        List<Inventory> emptyList = new ArrayList<>();
        doReturn(emptyList).when(inventoryDao).getInventoriesByProduct(973);


    }
}
