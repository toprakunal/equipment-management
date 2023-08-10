package com.example.equipmentmanagement.security;

import com.example.equipmentmanagement.converter.UserToUserDtoConverter;
import com.example.equipmentmanagement.dto.UserDto;
import com.example.equipmentmanagement.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final JwtProvider jwtProvider;

    private final UserToUserDtoConverter userToUserDtoConverter;

    public AuthService(JwtProvider jwtProvider, UserToUserDtoConverter userToUserDtoConverter) {
        this.jwtProvider = jwtProvider;
        this.userToUserDtoConverter = userToUserDtoConverter;
    }

    public Map<String,Object> createLoginInfo(Authentication authentication) {

        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        User user = principal.getUser();
        UserDto userDto = userToUserDtoConverter.convert(user);

        //Create a JWT.
        String token = jwtProvider.createToken(authentication);

        Map<String,Object> loginResultMap = new HashMap<>();

        loginResultMap.put("userInfo", userDto);
        loginResultMap.put("token", token);



        return loginResultMap;
    }
}
