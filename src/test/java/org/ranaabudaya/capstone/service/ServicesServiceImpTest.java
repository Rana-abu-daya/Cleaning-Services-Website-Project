package org.ranaabudaya.capstone.service;

import org.junit.jupiter.api.Test;
import org.ranaabudaya.capstone.entity.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ServicesServiceImpTest {
@Autowired
ServicesService servicesService;
    @Test
    void getServiceByName() {
        Services services = servicesService.getServiceByName("Window Cleaning");
        assertEquals(services.getId(), 1);
    }
}