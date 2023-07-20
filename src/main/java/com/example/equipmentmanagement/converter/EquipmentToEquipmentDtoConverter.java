package com.example.equipmentmanagement.converter;

import com.example.equipmentmanagement.dto.EquipmentDto;
import com.example.equipmentmanagement.model.Equipment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EquipmentToEquipmentDtoConverter implements Converter<Equipment, EquipmentDto> {


    @Override
    public EquipmentDto convert(Equipment source) {

        return new EquipmentDto(source.getSerialNo(),
                source.getName(),
                source.getEquipmentGroup(),
                source.getCost(),
                source.getDate(),
                source.getLocation(),
                source.getCompany(),
                source.getModel(),
                source.getStatus()
                );
    }
}
