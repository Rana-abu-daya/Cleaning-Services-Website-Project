package org.ranaabudaya.capstone.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.ranaabudaya.capstone.dto.CleanerDTO;
import org.ranaabudaya.capstone.entity.*;
import org.ranaabudaya.capstone.repository.BookingRepository;
import org.ranaabudaya.capstone.repository.CleanerRepository;
import org.ranaabudaya.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CleanerServiceImp implements CleanerService{
   CleanerRepository cleanerRepository;
   UserRepository userRepository;
   BookingRepository bookingRepository;
   @Autowired
   public CleanerServiceImp(BookingRepository bookingRepository,CleanerRepository cleanerRepository,UserRepository userRepository){
       this.cleanerRepository = cleanerRepository;
       this.userRepository = userRepository;
       this.bookingRepository =bookingRepository;
   }
    @Override
    public void create(CleanerDTO cleanerDTO) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Cleaner cleaner = modelMapper.map(cleanerDTO, Cleaner.class);
        User user = userRepository.findById(cleanerDTO.getUserId()).orElse(null);
        cleaner.setUser(user);
        cleanerRepository.save(cleaner);
    }
    public Cleaner findByUserId(int id){
      return cleanerRepository.findCleanerByUserId(id);


    }
    @Override
    public Optional<Cleaner> findCleanerById(int id) {

        return  cleanerRepository.findById(id);
    }
    public List<Cleaner> getAll(){
       return cleanerRepository.findAll();
    }

    @Override
    public Page<Cleaner> findAllCleanerPagination(Pageable pageable) {

       return cleanerRepository.findByIsNew(false,pageable);
    }

    public  int deleteById(int id){
       if(cleanerRepository.findById(id).isPresent()) {
         //userRepository.deleteById(cleanerRepository.findById(id).get().getUser().getId());
         cleanerRepository.deleteById(id);

           return 1;
       }
       else{
           return 0;
        }
    }
    public List<Cleaner> getCleanersByServiceId(int id){
       return cleanerRepository.findAllActiveByServicesId(id);
    }
    public List<Cleaner>  findAvailableCleanersForServiceAndTime(String startTime, int hours, LocalDate bookingDate, int serviceId){
       return cleanerRepository.findAvailableCleanersForServiceAndTime(startTime,hours,bookingDate,serviceId);
    }

    public List<Integer> checkUpdatedServices(int id,  Collection<Services> newServices){
        Cleaner cleaner = this.findCleanerById(id).get();
        List<Integer> result = new ArrayList<>();
        List<Integer> currentServiceIds = cleaner.getServices().stream()
                .map(Services::getId) // replace Service::getId with your getter method
                .collect(Collectors.toList());

        List<Integer> newServiceIds = newServices.stream()
                .map(Services::getId) // replace Services::getId with your getter method
                .collect(Collectors.toList());
        // Find services that are being dropped
        List<Integer> droppedServiceIds = currentServiceIds.stream()
                .filter(serviceId -> !newServiceIds.contains(serviceId))
                .collect(Collectors.toList());
        List<Booking.BookingStatus> statusList = Arrays.asList(Booking.BookingStatus.NEW, Booking.BookingStatus.IN_PROGRESS);

        // Check if there are any bookings for the cleaner and dropped services
        for (int serviceId : droppedServiceIds) {
            List<Booking> bookings = bookingRepository.findByCleanerIdAndServiceIdAndStatusIn(id, serviceId,statusList);
            if (!bookings.isEmpty()) {
                result.add(serviceId);
              }
        }
    return result;
   }
    public String calculateEndTime(String startTime, int hours) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime start = LocalTime.parse(startTime, formatter);
        LocalTime end = start.plusHours(hours);
        return end.format(formatter);
    }

    @Transactional
    public boolean updateCleanerSchedule(int cleanerId, String newStartTime, int newHours) {
        Cleaner cleaner = cleanerRepository.findById(cleanerId).get();
        // Check if there are any bookings during the cleaner's new working hours
        LocalTime newStart = LocalTime.parse(newStartTime);
        LocalTime newEnd = newStart.plusHours(newHours);
        List<Booking.BookingStatus> statusList = Arrays.asList(Booking.BookingStatus.NEW, Booking.BookingStatus.IN_PROGRESS);

        // Check the cleaner's bookings
        List<Booking> bookings = bookingRepository.findByStatusInAndCleanerId(statusList,cleanerId);
        for (Booking booking : bookings) {
            LocalTime bookingStart = LocalTime.parse(booking.getStartTime());
            LocalTime bookingEnd = bookingStart.plusHours(booking.getHours());

            // If the booking falls outside of the new working hours, throw an exception
            if (bookingStart.isBefore(newStart) || bookingEnd.isAfter(newEnd)) {
                return false;
            }
        }
        // Update the cleaner's start time and hours
       return true;
    }


}
