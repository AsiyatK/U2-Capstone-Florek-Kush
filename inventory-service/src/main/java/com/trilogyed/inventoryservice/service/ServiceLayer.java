package com.trilogyed.inventoryservice.service;

import com.trilogyed.inventoryservice.dao.InventoryDao;
import com.trilogyed.inventoryservice.exceptions.InventoryNotFoundException;
import com.trilogyed.inventoryservice.models.Inventory;
import com.trilogyed.inventoryservice.viewmodels.InventoryViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceLayer {

    private InventoryDao dao;

    @Autowired
    public ServiceLayer(InventoryDao dao){
        this.dao = dao;
    }

    public InventoryViewModel createInventory(InventoryViewModel inventory){

        Inventory inventoryCreated = dao.addProductToInventory(inventory);

        inventory.setInventoryId(inventoryCreated.getInventoryId());

        return inventory;

    }

    public InventoryViewModel getInventory(int inventoryId){

        Inventory i = dao.getInventory(inventoryId);

        if(i==null){
            throw new InventoryNotFoundException("The inventory with id " + inventoryId + " could not be located");
        } else{
            InventoryViewModel inventory = buildInventoryViewModel(i);

            if(inventory != null){
                return inventory;
            } else {
                throw new InventoryNotFoundException("The inventory with id " + inventoryId + " could not be located");
            }
        }

    }

    public List<InventoryViewModel> getAllInventories(){

        List<InventoryViewModel> inventories = buildInventoryViewModel(dao.getAllInventories());

        return inventories;

    }

    public List<InventoryViewModel> getInventoriesByProduct(int productId){

        List<InventoryViewModel> productInventories = buildInventoryViewModel(dao.getInventoriesByProduct(productId));
        List<InventoryViewModel> inventoryModels = new ArrayList<>();

        return productInventories;

    }

    public void updateInventory(InventoryViewModel updatedInventory){

        dao.updateInventory(updatedInventory);

    }

    public void deleteInventory(int inventoryId){

        dao.deleteInventory(inventoryId);

    }

    public void deleteInventoriesByProduct(int productId){

        dao.deleteInventoryByProduct(productId);

    }

    //Helper for converting from DTO to ViewModel
    private InventoryViewModel buildInventoryViewModel(Inventory inventory){
        InventoryViewModel inventoryModel = new InventoryViewModel();
        inventoryModel.setInventoryId(inventory.getInventoryId());
        inventoryModel.setProductId(inventory.getProductId());
        inventoryModel.setQuantity(inventory.getQuantity());

        return inventoryModel;
    }

    //Helper for converting list of DTOs to ViewModels
    private List<InventoryViewModel> buildInventoryViewModel(List<Inventory> inventories){

        List<InventoryViewModel> inventoryModels = new ArrayList<>();

        inventories.stream()
                .forEach(inventory -> {
                    InventoryViewModel model = buildInventoryViewModel(inventory);
                    inventoryModels.add(model);
                });

        return inventoryModels;
    }
}
