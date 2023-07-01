package org.ranaabudaya.capstone.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.ranaabudaya.capstone.dto.BookingDTO;
import org.ranaabudaya.capstone.entity.*;
import org.ranaabudaya.capstone.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public class BookingServiceImp implements BookingService{

    BookingRepository bookingRepository;
    ServicesService servicesService;
    CleanerService cleanerService;
    CustomerService customerService;

    @Autowired
    public BookingServiceImp(CustomerService customerService,BookingRepository bookingRepository,ServicesService servicesService, CleanerService cleanerService){
        this.bookingRepository = bookingRepository;
        this.servicesService = servicesService;
        this.cleanerService=cleanerService;
        this.customerService=customerService;
    }
    @Override
    public int deleteById(int id) {
        if(bookingRepository.findById(id).isPresent()) {
            Booking booking = bookingRepository.findById(id).get();
            if(booking.getStatus().equals(Booking.BookingStatus.NEW)){
                bookingRepository.deleteById(id);
                return 1;
            }else{
                return 0;
            }
        }
        else{
            return 0;
        }
    }
    public List<Booking> findByStatusInAndCleanerId(List<Booking.BookingStatus> statuses, int id){
        return bookingRepository.findByStatusInAndCleanerId(statuses,id);
    }

    @Override
    public void create(BookingDTO bookingDTO) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Booking newBooking = modelMapper.map(bookingDTO, Booking.class);
        Services service = servicesService.getServiceById(bookingDTO.getServiceId()).get();
        newBooking.setService(service);
        Cleaner cleaner = cleanerService.findCleanerById(bookingDTO.getCleanerId()).get();
        newBooking.setCleaner(cleaner);
        Customer customer= customerService.findCustomerByUserId(bookingDTO.getCustomerId());
        newBooking.setCustomer(customer);
        newBooking.setStatus(Booking.BookingStatus.NEW);
        newBooking.setPrice(service.getPrice());
        System.out.println(newBooking);
        bookingRepository.save(newBooking);

    }

    @Override
    public Optional<Booking> findBookingById(int id) {
        return  bookingRepository.findById(id);
    }

    @Override
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    @Override
    public void update(Booking booking) {
        bookingRepository.save(booking);

    }
    public List<Booking>findBookingByStatus(Booking.BookingStatus status){
        return bookingRepository.findByStatus(status);
    }
    public List<Booking>findBookingByDate(LocalDate date){
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        return bookingRepository.findByDate(sqlDate);
    }
    @Override
    public List<Booking> findBookingByCustomerId(int id){
        return bookingRepository.findBookingByCustomerId(id);
    }
    @Override
    public List<Booking> findBookingByCleanerId(int id){
        return bookingRepository.findBookingByCleanerId(id);
    }
}
