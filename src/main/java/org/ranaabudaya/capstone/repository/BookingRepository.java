package org.ranaabudaya.capstone.repository;

import org.ranaabudaya.capstone.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    public Page<Booking> findBookingByCustomerId(int id,Pageable pageable);
    public List<Booking> findBookingByCustomerId(int id);
    public List<Booking> findBookingByCleanerId(int id);
    public List<Booking> findByDate(Date date);
    public List<Booking> findByStatus(Booking.BookingStatus status);
    public Page<Booking>findBookingByCleanerId(int id,Pageable pageable);
    public List<Booking>findBookingsByCleanerIdAndServiceId(int id, int serviceId);
    public List<Booking> findBookingByServiceId(int serviceId);
    @Query(value = "SELECT * FROM booking b WHERE b.cleaner_id = :cleanerId AND " +
            "((b.start_time BETWEEN :startTime AND :endTime) OR (ADDTIME(b.start_time, SEC_TO_TIME(b.hours * 3600)) BETWEEN :startTime AND :endTime))", nativeQuery = true)
    List<Booking> findBookingsByCleanerIdAndTimeRange(int cleanerId,  String startTime,  String endTime);
    List<Booking> findByStatusInAndServiceId(List<Booking.BookingStatus> status, int serviceId);
    List<Booking> findByStatusInAndCleanerId(List<Booking.BookingStatus> status, int cleanerId);
    List<Booking> findByCleanerIdAndServiceIdAndStatusIn(int cleanerId, int serviceId, List<Booking.BookingStatus> status);
    List<Booking> findByCustomerIdAndStatus(int customerId, Booking.BookingStatus status);
    Page<Booking> findByDateAndStatusAndCleanerAndCustomerId(LocalDate date, Booking.BookingStatus status, int cleaner, int customerId, Pageable pageable);
    Page<Booking> findByDateAndStatusAndCleanerId(LocalDate date, Booking.BookingStatus status, int cleanerId, Pageable pageable);
}
