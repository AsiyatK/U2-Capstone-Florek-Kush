package com.trilogyed.inventoryservice.exceptions;

public class InventoryNotFoundException extends RuntimeException {

    public InventoryNotFoundException(){

    }

    public InventoryNotFoundException(String message){
        super(message);
    }
}
