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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CleanerRepositoryTest {

    @Autowired
    private CleanerRepository cleanerRepository;

    @Test
    public void testFindAllByServicesId() {
        Integer serviceId = 1702;
        List<Cleaner> cleaners = cleanerRepository.findAllByServicesId(serviceId);
        int expectedSize = 1;
        assertEquals(expectedSize, cleaners.size());
    }
    @Test
    public void testFindAllActiveByServicesId() {
        int serviceId = 1702;
        List<Cleaner> cleaners = cleanerRepository.findAllActiveByServicesId(serviceId);
        assertEquals(1, cleaners.size());
    }

    @Test
    public void testFindAvailableCleanersForServiceAndTime() {
        String startTime = "13:00";
        int hours = 1;
        LocalDate bookingDate = LocalDate.now().plusDays(7);//should be future date
        int serviceId = 1704;
        List<Cleaner> cleaners = cleanerRepository.findAvailableCleanersForServiceAndTime(startTime, hours, bookingDate, serviceId);
        assertEquals(2, cleaners.size());
    }
    @Test
    public void testFindCleanerByIsNew() {
        boolean isNew = true;
        List<Cleaner> cleaners = cleanerRepository.findCleanerByIsNew(isNew);
        assertEquals(2, cleaners.size());
    }

    @Test
    public void testFindByIsNew() {
        boolean isNew = true;
        Page<Cleaner> cleaners = cleanerRepository.findByIsNew(isNew, PageRequest.of(0, 10));
        assertEquals(2, cleaners.getTotalElements()); // replace expectedSize with the expected number of cleaners
    }

    @Test
    public void testFindByIsActive() {
        boolean isActive = true;
        Page<Cleaner> cleaners = cleanerRepository.findByIsActive(isActive, PageRequest.of(0, 10));
        assertEquals(3, cleaners.getTotalElements()); // replace expectedSize with the expected number of cleaners
    }

//    @Test
//    public void testDeleteCleanerServicesByCleanerId() {
//        int cleanerId = 1; // replace with an actual cleanerId
//        cleanerRepository.deleteCleanerServicesByCleanerId(cleanerId);
//        // Add assertions to check the effect of the deletion
//    }
//
//    @Test
//    public void testFindCleanerByUserId() {
//        int id = 1; // replace with an actual userId
//        Cleaner cleaner = cleanerRepository.findCleanerByUserId(id);
//        assertEquals(expectedCleaner, cleaner); // replace expectedCleaner with the expected Cleaner object
//    }
//
//    @Test
//    public void testFindByIsActiveAndIsNew() {
//        boolean isActive = true;
//        boolean isNew = true;
//        List<Cleaner> cleaners = cleanerRepository.findByIsActiveAndIsNew(isActive, isNew);
//        assertEquals(expectedSize, cleaners.size()); // replace expectedSize with the expected number of cleaners
//    }
//
//    @Test
//    public void testFindTop5Cleaner() {
//        List<Cleaner> cleaners = cleanerRepository.findTop5Cleaner();
//        assertEquals(5, cleaners.size());
//    }
}