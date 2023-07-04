package org.ranaabudaya.capstone.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ranaabudaya.capstone.entity.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//This for Services entity testing ,, the data is generated manually

@SpringBootTest
class ServicesRepositoryTest {
    @Autowired
    private ServicesRepository servicesRepository;

    private Services testService;

    @BeforeEach
    public void setUp() {
        // Create a Services object to use as test data
        testService = new Services();
       testService.setName("test");
       testService.setActive(true);
       testService.setPrice(10);
       testService.setDescription("This is test Service");
        servicesRepository.save(testService);
    }
@AfterEach
public void tearDown() {
    // Delete the test service from the repository
    servicesRepository.delete(testService);
    testService = null;
}
    @Test
    void findServicesByName() {
        String serviceName = "test";
        Services service = servicesRepository.findServicesByName(serviceName);
        assertEquals(testService.getDescription(), service.getDescription());
    }

    @Test
    void findByActiveTrue() {
        List<Services> services = servicesRepository.findByActiveTrue();
        assertEquals(4, services.size());
    }
}