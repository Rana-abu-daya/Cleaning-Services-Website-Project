package org.ranaabudaya.capstone.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ranaabudaya.capstone.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//This for Review entity testing ,, the data is generated manually

@SpringBootTest
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    BookingRepository bookingRepository;

    private Review testReview;
    @BeforeEach
    public void setUp() {
        int bookingId = 52;
        testReview = new Review();
        testReview.setComment("This is test Comment");
        testReview.setRatingValue(4);
        testReview.setBooking(bookingRepository.findById(bookingId).get());
        reviewRepository.save(testReview);
    }
    @AfterEach
    public void tearDown() {
        // Delete the test review from the repository
        reviewRepository.delete(testReview);
        testReview = null;
    }
    @Test
    void findByBookingId() {
        int bookingId = 52; // replace with an actual bookingId
        Review review = reviewRepository.findByBookingId(bookingId);
        assertEquals(testReview.getComment(), review.getComment());
    }
    @Test
    public void testDeleteByBookingId() {
        int bookingId = 52; // replace with an actual bookingId
        reviewRepository.deleteByBookingId(bookingId);
        Review review = reviewRepository.findByBookingId(bookingId);
        assertEquals(null, review);
    }

    @Test
    public void testFindTop3ByOrderByRatingValueDesc() {
        List<Review> reviews = reviewRepository.findTop3ByOrderByRatingValueDesc();
        assertEquals(2, reviews.size());
    }

    @Test
    void findByBookingCleanerId() {
        int cleanerId = 1; // replace with an actual cleanerId
        List<Review> reviews = reviewRepository.findByBookingCleanerId(cleanerId);
        assertEquals(2, reviews.size());
    }
}