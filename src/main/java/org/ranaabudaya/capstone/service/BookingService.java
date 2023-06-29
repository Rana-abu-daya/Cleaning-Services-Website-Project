package org.ranaabudaya.capstone.service;

import org.ranaabudaya.capstone.dto.AdminDTO;
import org.ranaabudaya.capstone.dto.BookingDTO;
import org.ranaabudaya.capstone.entity.Admin;
import org.ranaabudaya.capstone.entity.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingService {

    public int deleteById(int id);
    public void create(BookingDTO bookingDTO);

    public Optional<Booking> findBookingById(int id);
    public List<Booking> getAll();
    public void update(Booking booking);
}
