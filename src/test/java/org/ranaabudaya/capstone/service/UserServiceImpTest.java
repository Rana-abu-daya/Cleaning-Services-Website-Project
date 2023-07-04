package org.ranaabudaya.capstone.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.ranaabudaya.capstone.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImpTest {
@Autowired
UserService userService;
    @Test
    void findUserByEmail() {
        User user = userService.findUserByEmail("test@gmail.com");
        assertEquals(user.getUserName(), "rana");

    }
}