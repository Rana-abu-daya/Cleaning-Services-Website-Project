package org.ranaabudaya.capstone.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.ranaabudaya.capstone.entity.Admin;
import org.ranaabudaya.capstone.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

//This for Admin entity testing ,, the data is generated manually

@SpringBootTest
class AdminRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    AdminRepository adminRepository;

//this class to test admin Repoistory interface
    // by running the application an admin will added with email test@gmail.com
    @Test
    void findByUserId() {
        // in the database
        int id = 1;
        User user = userRepository.findUserByEmail("test@gmail.com");
        Admin admin=adminRepository.findByUserId(id);
        assertEquals(user.getId(), admin.getUser().getId());

    }
}