package com.trilogyed.inventoryservice.dao;

import com.trilogyed.inventoryservice.models.Inventory;

import java.util.List;

public interface InventoryDao {

    Inventory addProductToInventory(Inventory newInventory);

    Inventory getInventory(int inventoryId);

    List<Inventory> getInventoriesByProduct(int productId);

    List<Inventory> getAllInventories();

    void updateInventory(Inventory updateInventory);

    void deleteInventory(int inventoryId);

    void deleteInventoryByProduct(int productId);
}
