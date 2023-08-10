package com.example.equipmentmanagement.converter;

import com.example.equipmentmanagement.dto.UserDto;
import com.example.equipmentmanagement.model.User;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {


    @Override
    public UserDto convert(User source) {
        return new UserDto(source.getUserId(),
                source.getUserName(),
                source.getEmail(),
                source.getStatus(),
                source.getRole()
                );
    }
}
