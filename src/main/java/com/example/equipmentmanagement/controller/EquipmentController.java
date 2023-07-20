package com.example.equipmentmanagement.controller;

import com.example.equipmentmanagement.converter.EquipmentDtoToEquipmentConverter;
import com.example.equipmentmanagement.converter.EquipmentToEquipmentDtoConverter;
import com.example.equipmentmanagement.dto.EquipmentDto;
import com.example.equipmentmanagement.model.Equipment;
import com.example.equipmentmanagement.service.EquipmentService;
import com.example.equipmentmanagement.system.Result;
import com.example.equipmentmanagement.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/equipments")
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final EquipmentToEquipmentDtoConverter equipmentToEquipmentDtoConverter;

    private final EquipmentDtoToEquipmentConverter equipmentDtoToEquipmentConverter;


    public EquipmentController(EquipmentService equipmentService, EquipmentToEquipmentDtoConverter equipmentToEquipmentDtoConverter, EquipmentDtoToEquipmentConverter equipmentDtoToEquipmentConverter) {
        this.equipmentService = equipmentService;
        this.equipmentToEquipmentDtoConverter = equipmentToEquipmentDtoConverter;
        this.equipmentDtoToEquipmentConverter = equipmentDtoToEquipmentConverter;
    }

    @GetMapping
    public Result findAllEquipment(){

         List<Equipment> foundEquipment =  equipmentService.findAllEquipment();
         List<EquipmentDto> equipmentDtoList = foundEquipment.stream().map(equipmentToEquipmentDtoConverter::convert).collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS,"Find All Success",equipmentDtoList);
    }

    @GetMapping("/{equipmentId}")
    public Result findEquipmentById(@PathVariable String equipmentId) {
        Equipment foundEquipment = equipmentService.findEquipmentById(equipmentId);
        EquipmentDto equipmentDto = this.equipmentToEquipmentDtoConverter.convert(foundEquipment);
        return new Result(true, StatusCode.SUCCESS,"Find One Success",equipmentDto);
    }

    @PostMapping
    public Result addEquipment(@Valid @RequestBody EquipmentDto equipmentDto){
        Equipment equipment = equipmentDtoToEquipmentConverter.convert(equipmentDto);
        Equipment savedEquipment = equipmentService.addEquipment(equipment);

        EquipmentDto savedEquipmentDto = equipmentToEquipmentDtoConverter.convert(savedEquipment);
        return new Result(true,StatusCode.SUCCESS,"Add Success", savedEquipmentDto) ;
    }

    @PutMapping("/{equipmentId}")
    public Result updateEquipment(@PathVariable String equipmentId, @Valid @RequestBody EquipmentDto equipmentDto){
        Equipment update = equipmentDtoToEquipmentConverter.convert(equipmentDto);
        Equipment updatedEquipment = equipmentService.updateEquipment(equipmentId,update);
        EquipmentDto updatedEquipmentDto = equipmentToEquipmentDtoConverter.convert(updatedEquipment);

        return new Result(true,StatusCode.SUCCESS,"Update Success", updatedEquipmentDto);

    }

    @DeleteMapping("/{equipmentId}")
    public Result deleteEquipment(@PathVariable String equipmentId){
        equipmentService.deleteEquipment(equipmentId);
        return new Result(true,StatusCode.SUCCESS,"Delete Success");
    }






}


