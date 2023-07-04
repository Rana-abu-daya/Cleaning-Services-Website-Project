package org.ranaabudaya.capstone.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.ranaabudaya.capstone.entity.Cleaner;
import org.ranaabudaya.capstone.repository.CleanerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CleanerServiceImpTest {
@Autowired
CleanerService cleanerService;
@Autowired
    CleanerRepository cleanerRepository;

    @Test
    void findAllNewCleanerPagination() {
        List<Cleaner> cleaners = cleanerRepository.findByIsActiveAndIsNew(false,true);
        Page<Cleaner> cleanerList = cleanerService.findAllNewCleanerPagination(PageRequest.of(0, 10));
        assertEquals(cleaners.size(), cleanerList.getTotalElements());
    }
}