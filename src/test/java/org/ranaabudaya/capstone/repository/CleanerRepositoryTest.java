package org.ranaabudaya.capstone.repository;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.ranaabudaya.capstone.dto.ServicesDTO;
import org.ranaabudaya.capstone.entity.Cleaner;
import org.ranaabudaya.capstone.entity.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//this class for testing CleanerRepository query that used to retrive or manipulate cleaner object in
//the database.
@SpringBootTest
class CleanerRepositoryTest {

    @Autowired
    private CleanerRepository cleanerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    ServicesRepository servicesRepository;

    @Test
    public void testFindAllByServicesId() {
        Integer serviceId = 2;
        List<Cleaner> cleaners = cleanerRepository.findAllByServicesId(serviceId);
        int expectedSize = 2;
        assertEquals(expectedSize, cleaners.size());
    }
    @Test
    public void testFindAllActiveByServicesId() {
        int serviceId = 2;
        List<Cleaner> cleaners = cleanerRepository.findAllActiveByServicesId(serviceId);
        assertEquals(1, cleaners.size());
    }

    @Test
    public void testFindAvailableCleanersForServiceAndTime() {
        String startTime = "10:00";
        int hours = 2;
        LocalDate bookingDate = LocalDate.now().plusDays(7);//should be future date
        int serviceId = 2;
        List<Cleaner> cleaners = cleanerRepository.findAvailableCleanersForServiceAndTime(startTime, hours, bookingDate, serviceId);
        assertEquals(1, cleaners.size());
    }
    @Test
    public void testFindCleanerByIsNew() {
        boolean isNew = true;
        List<Cleaner> cleaners = cleanerRepository.findCleanerByIsNew(isNew);
        assertEquals(1, cleaners.size());
    }

    @Test
    public void testFindByIsNew() {
        boolean isNew = true;
        Page<Cleaner> cleaners = cleanerRepository.findByIsNew(isNew, PageRequest.of(0, 10));
        assertEquals(1, cleaners.getTotalElements()); // 
    }

    @Test
    public void testFindByIsActive() {
        boolean isActive = true;
        Page<Cleaner> cleaners = cleanerRepository.findByIsActive(isActive, PageRequest.of(0, 10));
        assertEquals(1, cleaners.getTotalElements()); // 
    }

//    @Test
//    public void testDeleteCleanerServicesByCleanerId() {
//        int cleanerId = 1; // replace with an actual cleanerId
//        cleanerRepository.deleteCleanerServicesByCleanerId(cleanerId);
//        // Add assertions to check the effect of the deletion
//    }
//
    @Test
    public void testFindCleanerByUserId() {
        int id = 152; // replace with an actual userId
        Cleaner cleaner = cleanerRepository.findCleanerByUserId(id);
        Cleaner expectedCleaner = new Cleaner();
        expectedCleaner.setUser(userRepository.findById(152).get());
        expectedCleaner.setNew(true);
        expectedCleaner.setActive(false);
        expectedCleaner.setAbout_me("This is Fad, I enjoy cleaning and organizing things");
        expectedCleaner.setHours(6);
        expectedCleaner.setStartTime("08:00");
        expectedCleaner.setResume("14128465-3745-4a5e-85fc-80603de2182f.pdf");
        expectedCleaner.setAverageRating(null);
        expectedCleaner.setId(2);
        List<Services> services = new ArrayList<>();
        services.add(servicesRepository.findById(2).get());
        services.add(servicesRepository.findById(3).get());
        expectedCleaner.setServices(services);


        assertEquals(expectedCleaner.getServices().size(), cleaner.getServices().size());
    }
//
    @Test
    public void testFindByIsActiveAndIsNew() {
        boolean isActive = true;
        boolean isNew = true;
        List<Cleaner> cleaners = cleanerRepository.findByIsActiveAndIsNew(isActive, isNew);
        assertEquals(0, cleaners.size()); // 
    }
//
    @Test
    public void testFindTop5Cleaner() {
        List<Cleaner> cleaners = cleanerRepository.findTop5Cleaner();
        assertEquals(1, cleaners.size());
    }
}