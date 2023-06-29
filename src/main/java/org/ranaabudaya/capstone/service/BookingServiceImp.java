package org.ranaabudaya.capstone.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.ranaabudaya.capstone.dto.BookingDTO;
import org.ranaabudaya.capstone.entity.*;
import org.ranaabudaya.capstone.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

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
            bookingRepository.deleteById(id);

            return 1;
        }
        else{
            return 0;
        }
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

    }
}
