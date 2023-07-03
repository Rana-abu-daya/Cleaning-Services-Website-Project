package org.ranaabudaya.capstone.service;

import org.ranaabudaya.capstone.dto.AdminDTO;
import org.ranaabudaya.capstone.dto.BookingDTO;
import org.ranaabudaya.capstone.entity.Admin;
import org.ranaabudaya.capstone.entity.Booking;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingService {

    public int deleteById(int id);
    public void create(BookingDTO bookingDTO);

    public Optional<Booking> findBookingById(int id);
    public List<Booking> getAll();
    public void update(Booking booking);
    public List<Booking>findBookingByStatus(Booking.BookingStatus status);
    public List<Booking>findBookingByDate(LocalDate date);

    public List<Booking> findBookingByCustomerId(int id);
    public List<Booking> findBookingByCleanerId(int id);
    public List<Booking> findByStatusInAndCleanerId(List<Booking.BookingStatus> statuses, int id);
    public List<Booking> findByStatusInAndCustomerId(List<Booking.BookingStatus> statuses, int id);
    public  double findTotalMony(Booking.BookingStatus status);
    public long countByServiceId(int servId);

}
