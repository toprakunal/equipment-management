package com.example.equipmentmanagement.exception;

public class EquipmentNotFoundException extends RuntimeException{
    public EquipmentNotFoundException(String id){
        super("Could not found equipment with serial no: "+ id);
    }
}
