package org.ranaabudaya.capstone.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.ranaabudaya.capstone.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RoleServiceImpTest {
    @Autowired
    RoleService roleService;

    @Test
    void findRoleByRoleName() {
        Role role = roleService.findRoleByRoleName("ROLE_ADMIN");
        assertEquals(role.getName(), "ROLE_ADMIN");

    }
}