package com.example.equipmentmanagement.converter;

import com.example.equipmentmanagement.dto.EquipmentDto;
import com.example.equipmentmanagement.model.Equipment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;




@Component
public class EquipmentDtoToEquipmentConverter implements Converter<EquipmentDto,Equipment> {


    @Override
    public Equipment convert(EquipmentDto source) {
        Equipment equipment = new Equipment();

        equipment.setSerialNo(source.serialNo());
        equipment.setName(source.name());
        equipment.setEquipmentGroup(source.equipmentGroup());
        equipment.setCost(source.cost());
        equipment.setStatus(source.status());
        equipment.setDate(source.date());
        equipment.setCompany(source.company());
        equipment.setLocation(source.location());
        equipment.setModel(source.model());

        return equipment;

    }
}
