package com.example.equipmentmanagement.dto;

import jakarta.validation.constraints.NotNull;

public record UserDto(@NotNull
                      Integer userId,
                      @NotNull
                      String userName,
                      @NotNull
                      String email,
                      String status,
                      @NotNull
                      String password) {
}
