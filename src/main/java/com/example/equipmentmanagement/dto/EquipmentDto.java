package com.example.equipmentmanagement.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record EquipmentDto(@NotEmpty(message = "Serial No is required.")
                           String serialNo,
                           @NotEmpty(message = "Name is required.")
                           String name,
                           @NotEmpty(message = "Group No is required.")
                           String equipmentGroup,
                           @NotNull(message = "Cost is required.")
                           Double cost,
                           @NotEmpty(message = "Date is required.")
                           String date,
                           String location,
                           String company,
                           String model,
                           String status
                           ) {
}
