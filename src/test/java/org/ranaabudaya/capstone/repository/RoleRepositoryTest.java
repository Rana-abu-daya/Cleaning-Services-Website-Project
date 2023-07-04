package org.ranaabudaya.capstone.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ranaabudaya.capstone.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//This for Role entity testing ,, the data is generated manually

@SpringBootTest
class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    private Role testRole;
    @BeforeEach
    public void setUp() {

        testRole = new Role();
        testRole.setName("Test");
        roleRepository.save(testRole);
    }
    @AfterEach
    public void tearDown() {
        // Delete the test role from the repository
        roleRepository.delete(testRole);
        testRole = null;
    }
    @Test
    public void testFindRoleByName() {
        String roleName = "Test";
        Role role = roleRepository.findRoleByName(roleName);
        assertEquals(testRole.getId(), role.getId());
    }


    @Test
    public void testFindRoleByUser() {
        int userId = 52;
        List<Role> roles = roleRepository.findRoleByUser(userId);
        assertEquals(1, roles.size());
    }
}