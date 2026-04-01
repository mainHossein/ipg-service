package com.example.ipgserviceproject.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    void nationalIdValid() {
        System.out.println(userService.nationalIdValid(1000000000));
    }

    @Test
    void getUserInfo() {
        System.out.println(userService.getUserInfo(1000000000));
    }
}