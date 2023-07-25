package com.example.equipmentmanagement.service;

import com.example.equipmentmanagement.exception.UserNotFoundException;
import com.example.equipmentmanagement.model.User;
import com.example.equipmentmanagement.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    List<User> userList = new ArrayList<>();



    @BeforeEach
    void setUp() {
        User u1 = new User();
        u1.setUserId(1);
        u1.setUserName("User1");
        u1.setEmail("user1@hotmail.com");
        u1.setUserPassword("user1Password");
        u1.setStatus("active");
        userList.add(u1);

        User u2 = new User();
        u2.setUserId(2);
        u2.setUserName("User2");
        u2.setEmail("user2@hotmail.com");
        u2.setUserPassword("user2Password");
        u2.setStatus("active");
        userList.add(u2);


        User u3 = new User();
        u3.setUserId(3);
        u3.setUserName("User3");
        u3.setEmail("user3@hotmail.com");
        u3.setUserPassword("user3Password");
        u3.setStatus("active");
        userList.add(u3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindUserByIdSuccess
            () {
        //Given
        User user = new User();
        user.setUserId(1);
        user.setUserName("mockUser");
        user.setEmail("mockuser@hotmail.com");
        user.setUserPassword("mock_password");
        user.setStatus("Active");

        given(userRepository.findById(1)).willReturn(Optional.of(user));

        User returnedUser = userService.findUserById(1);

        assertThat(returnedUser.getUserId()).isEqualTo(user.getUserId());
        assertThat(returnedUser.getUserName()).isEqualTo(user.getUserName());
        assertThat(returnedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(returnedUser.getUserPassword()).isEqualTo(user.getUserPassword());
        assertThat(returnedUser.getStatus()).isEqualTo(user.getStatus());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testFindUserByIdNotFound(){
        //Given
        given(userRepository.findById(1)).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, ()-> {
            userService.findUserById(1);
        });

        verify(userRepository, times(1)).findById(1);

    }

    @Test
    void testFindAllUsersSuccess(){
        given(userRepository.findAll()).willReturn(this.userList);

        List<User> actualUsers = userService.findAllUser();

        assertThat(actualUsers.size()).isEqualTo(userList.size());
        verify(userRepository,times(1)).findAll();

    }

    @Test
    void testCreateUserSuccess(){

        User newUser = new User();
        newUser.setUserId(4);
        newUser.setUserName("mockUser4");
        newUser.setEmail("mockuser4@hotmail.com");
        newUser.setUserPassword("mock_password4");
        newUser.setStatus("Active");

        given(userRepository.save(newUser)).willReturn(newUser);

        User savedUser = userService.createUser(newUser);

        assertThat(savedUser.getUserId()).isEqualTo(4);
        assertThat(savedUser.getUserName()).isEqualTo("mockUser4");
        assertThat(savedUser.getUserPassword()).isEqualTo("mock_password4");
        assertThat(savedUser.getEmail()).isEqualTo("mockuser4@hotmail.com");
        assertThat(savedUser.getStatus()).isEqualTo("Active");
        verify(userRepository, times(1)).save(newUser);

    }

    @Test
    void testUpdateUserSuccess(){
        User oldUser = new User();
        oldUser.setUserId(4);
        oldUser.setUserName("mockUser4");
        oldUser.setEmail("mockuser4@hotmail.com");
        oldUser.setUserPassword("mock_password4");
        oldUser.setStatus("Active");

        User update = new User();
        update.setUserId(4);
        update.setUserName("updatedUserName");
        update.setEmail("mockuser4@hotmail.com");
        update.setUserPassword("mock_password4");
        update.setStatus("Active");

        given(userRepository.findById(4)).willReturn(Optional.of(oldUser));
        given(userRepository.save(oldUser)).willReturn(oldUser);

        User updatedUser =  userService.updateUser(4,update);

        assertThat(updatedUser.getUserId()).isEqualTo(update.getUserId());
        assertThat(updatedUser.getUserName()).isEqualTo(update.getUserName());
        assertThat(updatedUser.getUserPassword()).isEqualTo(update.getUserPassword());

        verify(userRepository,times(1)).findById(4);
        verify(userRepository,times(1)).save(oldUser);
    }

    @Test
    void testUpdateUserNotFound(){
        User update = new User();
        update.setUserId(4);
        update.setUserName("mockUser4");
        update.setEmail("mockuser4@hotmail.com");
        update.setUserPassword("mock_password4");
        update.setStatus("Active");


        given(userRepository.findById(4)).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,() ->{
            userService.updateUser(4,update);
        } );

        verify(userRepository,times(1)).findById(4);
    }

    @Test
    void testDeleteUserSuccess(){
        User user = new User();
        user.setUserId(4);
        user.setUserName("mockUser4");
        user.setEmail("mockuser4@hotmail.com");
        user.setUserPassword("mock_password4");
        user.setStatus("Active");

        given(userRepository.findById(4)).willReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(4);

        userService.deleteUser(4);

        verify(userRepository, times(1)).deleteById(4);
    }

    @Test
    void testDeleteUserNotFound(){
        given(userRepository.findById(4)).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, ()->{
            userService.deleteUser(4);
        });

        verify(userRepository, times(1)).findById(4);
    }




}