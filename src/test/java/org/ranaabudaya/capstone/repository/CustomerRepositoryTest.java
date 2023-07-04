package org.ranaabudaya.capstone.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ranaabudaya.capstone.dto.UserDTO;
import org.ranaabudaya.capstone.entity.Customer;
import org.ranaabudaya.capstone.entity.User;
import org.ranaabudaya.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//This for customer entity testing ,, the data is generated manually
@SpringBootTest
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    UserService userService;

    private Customer testCustomer;
    @BeforeEach
    public void setUp() {
        UserDTO userDTO = new UserDTO("customer", "customer", "new", "testCustomer@gmail.com", "70174312345", "98201", "1234",
                "1234", "2900 grand ave", "Everett", "wa");
        userDTO.setRoleName("ROLE_CLIENT");
        int id = userService.create(userDTO);
        User usernew = userService.findById(id).get();
        testCustomer = new Customer();
        testCustomer.setUser(usernew);
        testCustomer.setDeleted(false);
        customerRepository.save(testCustomer);
        System.out.println(testCustomer);
    }

    @AfterEach
    public void tearDown() {
        // Delete the test customer from the repository
        customerRepository.delete(testCustomer);
        testCustomer = null;
    }
    @Test
    void findCustomerByUserId() {
        User usernew = userService.findUserByEmail("testCustomer@gmail.com");
        int id = usernew.getId();
        Customer customer = customerRepository.findCustomerByUserId(id);
        assertEquals(testCustomer.getId(), customer.getId());

    }

    @Test
    void findByIsDeletedTrue() {
//        List<Customer> customers = customerRepository.findByIsDeletedTrue();
//        assertEquals(0, customers.size());
        //Another senario
        testCustomer.setDeleted(true);
        customerRepository.save(testCustomer);
        List<Customer> customers = customerRepository.findByIsDeletedTrue();
        assertEquals(1, customers.size());

    }

    @Test
    public void testFindByIsDeletedTruePageable() {
        testCustomer.setDeleted(true);
        customerRepository.save(testCustomer);
            Page<Customer> customers = customerRepository.findByIsDeletedTrue(PageRequest.of(0, 10));
            assertEquals(1, customers.getTotalElements());
    }

    @Test
    void findByIsDeletedFalse() {
        Page<Customer> customers = customerRepository.findByIsDeletedFalse(PageRequest.of(0, 10));
        assertEquals(customerRepository.findAll().size(), customers.getTotalElements());

    }

    @Test
    void testFindByIsDeletedFalse() {
        testCustomer.setDeleted(true);
        customerRepository.save(testCustomer);
        List<Customer> customers = customerRepository.findByIsDeletedFalse();
        assertEquals(customerRepository.findAll().size()-1, customers.size());
    }
}