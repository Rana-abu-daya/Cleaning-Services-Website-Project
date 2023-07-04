package org.ranaabudaya.capstone.service;

import org.junit.jupiter.api.Test;
import org.ranaabudaya.capstone.entity.Admin;
import org.ranaabudaya.capstone.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AdminServiceImpTest {
    @Autowired
    AdminService adminService;
    @Autowired
    AdminRepository adminRepository;
    @Test
    void getAll() {
        List<Admin> adminList = adminService.getAll();
        assertEquals(adminRepository.findAll().size(), adminList.size());
    }
}