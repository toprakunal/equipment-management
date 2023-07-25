package com.example.equipmentmanagement.converter;

import com.example.equipmentmanagement.dto.UserDto;
import com.example.equipmentmanagement.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserConverter implements Converter<UserDto, User> {


    @Override
    public User convert(UserDto source) {
        User user = new User();

        user.setUserId(source.userId());
        user.setUserName(source.userName());
        user.setEmail(source.email());
        user.setStatus(source.status());

        return user;

    }
}
