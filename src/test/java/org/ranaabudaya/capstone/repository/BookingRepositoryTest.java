package org.ranaabudaya.capstone.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.ranaabudaya.capstone.entity.Booking;
import org.ranaabudaya.capstone.entity.Cleaner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

//tesing the booking REpository Using data in the database
@SpringBootTest
class BookingRepositoryTest {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ServicesRepository servicesRepository;

    private Booking testBooking;
    private CleanerRepository cleanerRepository;


    @Test
    void findBookingByCustomerId() {
        int custId = 2;
       List<Booking> bookingList=  bookingRepository.findBookingByCustomerId(2);
        assertEquals(3, bookingList.size());
    }

    @Test
    void testFindBookingByCustomerId() {
        int custId = 2;
        Page<Booking> bookingList = bookingRepository.findBookingByCustomerId(custId, PageRequest.of(0, 10));
        assertEquals(3, bookingList.getTotalElements());
    }

    @Test
    void findBookingByCleanerId() {
        int cleanerId = 1;
        List<Booking> bookingList=  bookingRepository.findBookingByCleanerId(cleanerId);
        assertEquals(3, bookingList.size());
    }


    @Test
    void findByStatus() {
        Booking.BookingStatus status = Booking.BookingStatus.NEW;
        List<Booking> bookings = bookingRepository.findByStatus(status);
        assertEquals(1, bookings.size());
    }


    @Test
    void findBookingsByCleanerIdAndServiceId() {
        List<Booking> bookings = bookingRepository.findBookingsByCleanerIdAndServiceId(1,1);
        assertEquals(1, bookings.size());
    }

    @Test
    void findBookingByServiceId() {
        List<Booking> bookings = bookingRepository.findBookingByServiceId(1);
        assertEquals(1, bookings.size());
    }

    @Test
    void findBookingsByCleanerIdAndTimeRange() {
        int cleanerId = 1; // replace with an actual cleanerId
        String startTime = "10:00"; // replace with an actual startTime
        String endTime = "12:00"; // replace with an actual endTime
        List<Booking> bookings = bookingRepository.findBookingsByCleanerIdAndTimeRange(cleanerId, startTime, endTime);
        assertEquals(3, bookings.size());
    }

    @Test
    void findByStatusInAndServiceId() {
        List<Booking.BookingStatus> statusList = Arrays.asList(Booking.BookingStatus.NEW, Booking.BookingStatus.CANCELLED); // replace with actual statuses
        int serviceId = 1; // replace with an actual serviceId
        List<Booking> bookings = bookingRepository.findByStatusInAndServiceId(statusList, serviceId);
        assertEquals(1, bookings.size());
    }

    @Test
    void findByStatusInAndCleanerId() {
        List<Booking.BookingStatus> statusList = Arrays.asList(Booking.BookingStatus.NEW, Booking.BookingStatus.CANCELLED); // replace with actual statuses
        int cleanerId = 1; // replace with an actual cleanerId
        List<Booking> bookings = bookingRepository.findByStatusInAndCleanerId(statusList, cleanerId);
        assertEquals(2, bookings.size());

    }

    @Test
    void findByStatusInAndCustomerId() {
        List<Booking.BookingStatus> statusList = Arrays.asList(Booking.BookingStatus.NEW, Booking.BookingStatus.CANCELLED); // replace with actual statuses
        int customerId = 2; // replace with an actual customerId
        List<Booking> bookings = bookingRepository.findByStatusInAndCustomerId(statusList, customerId);
        assertEquals(2, bookings.size());
    }

    @Test
    void findByCleanerIdAndServiceIdAndStatusIn() {
        int cleanerId = 1; // replace with an actual cleanerId
        int serviceId = 1; // replace with an actual serviceId
        List<Booking.BookingStatus> statusList = Arrays.asList(Booking.BookingStatus.NEW); // replace with actual statuses
        List<Booking> bookings = bookingRepository.findByCleanerIdAndServiceIdAndStatusIn(cleanerId, serviceId, statusList);
        assertEquals(1, bookings.size());
    }



    @Test
    void findByDateAndStatusAndCleanerAndCustomerId() {
        LocalDate date = LocalDate.now().plusDays(11);
        Booking.BookingStatus status = Booking.BookingStatus.NEW;
        int cleanerId = 1;
        int customerId = 2;
        Page<Booking> bookings = bookingRepository.findByDateAndStatusAndCleanerAndCustomerId(date, status, cleanerId, customerId, PageRequest.of(0, 10));
        assertEquals(0, bookings.getTotalElements());
    }

    @Test
    void findByDateAndStatusAndCleanerId() {
        LocalDate date = LocalDate.now().plusDays(11);

        Booking.BookingStatus status = Booking.BookingStatus.NEW;
        int cleanerId = 1;
        Page<Booking> bookings = bookingRepository.findByDateAndStatusAndCleanerId(date, status, cleanerId, PageRequest.of(0, 10));
        assertEquals(1, bookings.getTotalElements());
    }

    @Test
    void findTotalMoney() {
        double money= bookingRepository.findTotalMoney(Booking.BookingStatus.SUCCESS);
        assertEquals(12*2.0, money);
    }

    @Test
    void countByServiceId() {
        int serviceId = 1;
        long count = bookingRepository.countByServiceId(serviceId);
        assertEquals(1, count);
    }


    @ParameterizedTest
    @MethodSource("provideStatusAndCleanerId")
    public void testFindByStatusInAndCleanerId(List<Booking.BookingStatus> statusList, int cleanerId) {
        List<Booking> bookings = bookingRepository.findByStatusInAndCleanerId(statusList, cleanerId);
        assertEquals(1, bookings.size());
    }

    private static Stream<Arguments> provideStatusAndCleanerId() {
        return Stream.of(
                Arguments.of(Arrays.asList(Booking.BookingStatus.NEW), 1),
                Arguments.of(Arrays.asList(Booking.BookingStatus.SUCCESS), 1),
                Arguments.of(Arrays.asList( Booking.BookingStatus.CANCELLED), 1)
        );
    }
}