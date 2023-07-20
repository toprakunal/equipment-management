package com.example.equipmentmanagement.service;

import com.example.equipmentmanagement.exception.EquipmentNotFoundException;
import com.example.equipmentmanagement.model.Equipment;
import com.example.equipmentmanagement.repository.EquipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public List<Equipment> findAllEquipment(){
         return equipmentRepository.findAll();
    }

    public Equipment findEquipmentById(String equipmentId){
        return equipmentRepository.findById(equipmentId)
                .orElseThrow(()-> new EquipmentNotFoundException(equipmentId));
    }

    public Equipment addEquipment(Equipment equipment){
        return equipmentRepository.save(equipment);
    }

    public Equipment updateEquipment(String equipmentId, Equipment update){

        //Fluent Interface
        return equipmentRepository.findById(equipmentId).map(equipment -> {
            equipment.setName(update.getName());
            equipment.setEquipmentGroup(update.getEquipmentGroup());
            equipment.setCost(update.getCost());
            equipment.setCompany(update.getCompany());
            equipment.setModel(update.getModel());
            equipment.setDate(update.getDate());
            equipment.setLocation(update.getLocation());
            equipment.setStatus(update.getStatus());
            return this.equipmentRepository.save(equipment);
        })
                .orElseThrow(()-> new EquipmentNotFoundException(equipmentId));


    }

    public void deleteEquipment(String equipmentId){
        equipmentRepository.findById(equipmentId)
                .orElseThrow(()-> new EquipmentNotFoundException(equipmentId));

        equipmentRepository.deleteById(equipmentId);
    }




}
