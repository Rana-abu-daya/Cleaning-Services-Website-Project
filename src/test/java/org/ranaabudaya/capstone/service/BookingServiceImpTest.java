package org.ranaabudaya.capstone.service;

import org.junit.jupiter.api.Test;
import org.ranaabudaya.capstone.entity.Booking;
import org.ranaabudaya.capstone.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BookingServiceImpTest {
    @Autowired
    BookingService bookingService;
    @Autowired
    BookingRepository bookingRepository;
    @Test
    void findBookingByStatus() {
       List<Booking> bookingList= bookingRepository.findByStatus(Booking.BookingStatus.IN_PROGRESS);
       List<Booking> bookingList1 = bookingService.findBookingByStatus(Booking.BookingStatus.IN_PROGRESS);
        assertEquals(bookingList.size(), bookingList1.size());
    }
}