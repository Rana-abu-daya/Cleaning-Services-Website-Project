package org.ranaabudaya.capstone.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.ranaabudaya.capstone.dto.CustomerDTO;
import org.ranaabudaya.capstone.dto.UserDTO;
import org.ranaabudaya.capstone.entity.Customer;
import org.ranaabudaya.capstone.entity.User;
import org.ranaabudaya.capstone.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CustomerServiceImpTest {
    @Autowired
    CustomerService customerService;
    @Autowired
    UserService userService;
    private Customer testCustomer;
    @Autowired
    CustomerRepository customerRepository;

    @Test
    void create() {
        UserDTO userDTO = new UserDTO("customer", "customer", "new", "testCustomer@gmail.com", "70174312345", "98201", "1234",
                "1234", "2900 grand ave", "Everett", "wa");
        userDTO.setRoleName("ROLE_CLIENT");
        int id = userService.create(userDTO);
        User usernew = userService.findById(id).get();
        CustomerDTO customerDTO  = new CustomerDTO();
        customerDTO.setUserId(id);
        customerService.create(customerDTO);
        testCustomer = customerService.findCustomerByUserId(id);
        System.out.println(testCustomer);
        assertEquals(testCustomer.toString(), customerService.findCustomerById(testCustomer.getId()).get().toString());
    }
    @AfterEach
    public void tearDown() {
        // Delete the test customer from the repository
        customerRepository.delete(testCustomer);
        testCustomer = null;
    }
}