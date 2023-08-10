package com.example.equipmentmanagement.service;


import com.example.equipmentmanagement.exception.UserNotFoundException;
import com.example.equipmentmanagement.model.User;
import com.example.equipmentmanagement.repository.UserRepository;
import com.example.equipmentmanagement.security.MyUserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUserById(Integer userId){
        return userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));
    }

    public List<User> findAllUser(){
        return userRepository.findAll();
    }

    public User createUser(User user){
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        return userRepository.save(user);

    }

    public User updateUser(Integer userId,User update) {
        return userRepository.findById(userId).map(user -> {
                 user.setUserName(update.getUserName());
                 user.setUserPassword(update.getUserPassword());
                 user.setEmail(update.getEmail());
                 user.setStatus(update.getStatus());
                 user.setRole(update.getRole());
                 return userRepository.save(user);
                }).orElseThrow(()-> new UserNotFoundException(4));
    }

    public void deleteUser(Integer userId){
        userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));
        userRepository.deleteById(userId);


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username)
                .map(user -> new MyUserPrincipal(user))
                .orElseThrow(() ->new UsernameNotFoundException("Username "+ username + " not found."));

    }
}
