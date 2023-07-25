package com.example.equipmentmanagement.controller;


import com.example.equipmentmanagement.converter.UserDtoToUserConverter;
import com.example.equipmentmanagement.converter.UserToUserDtoConverter;
import com.example.equipmentmanagement.dto.UserDto;
import com.example.equipmentmanagement.model.User;
import com.example.equipmentmanagement.service.UserService;
import com.example.equipmentmanagement.system.Result;
import com.example.equipmentmanagement.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    private final UserToUserDtoConverter userToUserDtoConverter;

    private final UserDtoToUserConverter userDtoToUserConverter;


    public UserController(UserService userService, UserToUserDtoConverter userToUserDtoConverter, UserDtoToUserConverter userDtoToUserConverter) {
        this.userService = userService;
        this.userToUserDtoConverter = userToUserDtoConverter;
        this.userDtoToUserConverter = userDtoToUserConverter;
    }


    @GetMapping("/{userId}")
    public Result findUserById(@PathVariable Integer userId){
        User foundUser = userService.findUserById(userId);
        UserDto userDto = userToUserDtoConverter.convert(foundUser);
        return new Result(true, StatusCode.SUCCESS,"Find One Success.", userDto);
    }

    @GetMapping
    public Result findAllUsers(){
        List<User> foundUsers = userService.findAllUser();
        List<UserDto> foundUserDtoList = foundUsers.stream().map(userToUserDtoConverter::convert).collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS,"Find All Success.", foundUserDtoList);
    }

    @PostMapping
    public Result createUser(@Valid @RequestBody UserDto userDto){
        User user = userDtoToUserConverter.convert(userDto);
        userService.createUser(user);
        UserDto savedUserDto = userToUserDtoConverter.convert(user);
        return new Result(true,StatusCode.SUCCESS,"Add One Success.", savedUserDto);
    }

    @PutMapping("/{userId}")
    public Result updateUser(@PathVariable Integer userId, @Valid @RequestBody UserDto userDto){
        User update = userDtoToUserConverter.convert(userDto);
        User updatedUser = userService.updateUser(userId,update);
        UserDto updatedUserDto = userToUserDtoConverter.convert(updatedUser);
        return new Result(true,StatusCode.SUCCESS,"Update One Success.", updatedUserDto);
    }

    @DeleteMapping("/{userId}")
    public Result deleteUser(@PathVariable Integer userId){
        userService.deleteUser(userId);
        return new Result(true,StatusCode.SUCCESS,"Delete One Success.");

    }

}
