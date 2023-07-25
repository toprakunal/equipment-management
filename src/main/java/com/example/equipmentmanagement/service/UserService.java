package com.example.equipmentmanagement.service;


import com.example.equipmentmanagement.exception.UserNotFoundException;
import com.example.equipmentmanagement.model.User;
import com.example.equipmentmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(Integer userId){
        return userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));
    }

    public List<User> findAllUser(){
        return userRepository.findAll();
    }

    public User createUser(User user){
        return userRepository.save(user);

    }

    public User updateUser(Integer userId,User update) {
        return userRepository.findById(userId).map(user -> {
                 user.setUserName(update.getUserName());
                 user.setUserPassword(update.getUserPassword());
                 user.setEmail(update.getEmail());
                 user.setStatus(update.getStatus());
                 return userRepository.save(user);
                }).orElseThrow(()-> new UserNotFoundException(4));
    }

    public void deleteUser(Integer userId){
        userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));
        userRepository.deleteById(userId);


    }
}
