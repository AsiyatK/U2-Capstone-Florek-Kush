package com.trilogyed.inventoryservice.dao;

import com.trilogyed.inventoryservice.models.Inventory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InventoryDaoTest {

    @Autowired
    InventoryDao inventoryDao;

    @Before
    public void setUp(){
        List<Inventory> inventories = inventoryDao.getAllInventories();

        inventories.stream()
                .forEach(inventory -> inventoryDao.deleteInventory(inventory.getInventoryId()));
    }

    @After
    public void tearDown(){
        List<Inventory> inventories = inventoryDao.getAllInventories();

        inventories.stream()
                .forEach(inventory -> inventoryDao.deleteInventory(inventory.getInventoryId()));
    }

    @Test
    public void addGetGetByProductDeleteInventory(){
        //add, get
        Inventory inventory = new Inventory();
        inventory.setProductId(732);
        inventory.setQuantity(82);

        inventory = inventoryDao.addProductToInventory(inventory);

        Inventory inventoryFromDb = inventoryDao.getInventory(inventory.getInventoryId());

        assertEquals(inventory, inventoryFromDb);

        //get by customerId
        List<Inventory> inventories = new ArrayList<>();
        inventories.add(inventory);

        List<Inventory> inventoriesByProduct = inventoryDao.getInventoriesByProduct(inventory.getProductId());

        assertEquals(inventories, inventoriesByProduct);
        assertEquals(1, inventoriesByProduct.size());

        //delete
        inventoryDao.deleteInventory(inventory.getInventoryId());

        inventory = inventoryDao.getInventory(inventory.getInventoryId());

        assertNull(inventory);

    }

    @Test
    public void updateInventory(){

        Inventory inventory = new Inventory();
        inventory.setProductId(732);
        inventory.setQuantity(82);

        inventory = inventoryDao.addProductToInventory(inventory);

        //update in local memory
        inventory.setQuantity(50);

        //set new instance equal to db version
        Inventory inventoryFromDb = inventoryDao.getInventory(inventory.getInventoryId());

        assertNotEquals(inventory, inventoryFromDb);

        inventoryDao.updateInventory(inventory);

        inventoryFromDb = inventoryDao.getInventory(inventory.getInventoryId());

        assertEquals(inventory, inventoryFromDb);

    }

    @Test
    public void deleteByProduct(){
        Inventory inventory = new Inventory();
        inventory.setProductId(732);
        inventory.setQuantity(82);

        inventory = inventoryDao.addProductToInventory(inventory);

        List<Inventory> inventories = new ArrayList<>();
        inventories.add(inventory);

        List<Inventory> inventoriesByProduct = inventoryDao.getInventoriesByProduct(inventory.getProductId());

        assertEquals(inventories, inventoriesByProduct);
        assertEquals(1, inventoriesByProduct.size());

        //delete
        inventoryDao.deleteInventoryByProduct(inventory.getProductId());

        inventoriesByProduct = inventoryDao.getInventoriesByProduct(inventory.getProductId());

        assertEquals(0, inventoriesByProduct.size());
    }

    @Test
    public void getAllInvoices(){

        List<Inventory> inventories = inventoryDao.getAllInventories();

        assertEquals(0, inventories.size());

        Inventory inventory = new Inventory();
        inventory.setProductId(732);
        inventory.setQuantity(82);

        inventoryDao.addProductToInventory(inventory);

        inventories = inventoryDao.getAllInventories();

        assertEquals(1, inventories.size());

        inventory = new Inventory();
        inventory.setProductId(908);
        inventory.setQuantity(82);

        inventoryDao.addProductToInventory(inventory);

        inventories = inventoryDao.getAllInventories();

        assertEquals(2, inventories.size());

    }
}
