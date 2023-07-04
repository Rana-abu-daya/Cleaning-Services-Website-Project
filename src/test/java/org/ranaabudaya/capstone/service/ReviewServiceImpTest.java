package org.ranaabudaya.capstone.service;

import org.junit.jupiter.api.Test;
import org.ranaabudaya.capstone.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReviewServiceImpTest {
    @Autowired
    ReviewService reviewService;
    @Test
    void findBookingById() {
        int booingid=1;
        Review reviewList = reviewService.findBookingById(booingid);
        assertEquals(reviewList.getId(), 102);
    }
}