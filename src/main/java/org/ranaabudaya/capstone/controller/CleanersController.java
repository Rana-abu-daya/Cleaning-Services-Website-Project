package org.ranaabudaya.capstone.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.ranaabudaya.capstone.dto.UserDTO;
import org.ranaabudaya.capstone.entity.Admin;
import org.ranaabudaya.capstone.entity.Booking;
import org.ranaabudaya.capstone.entity.Services;
import org.ranaabudaya.capstone.repository.CleanerRepository;
import org.ranaabudaya.capstone.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ranaabudaya.capstone.entity.Cleaner;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@Slf4j
public class CleanersController {
    CleanerRepository cleanerRepository;
    CleanerService cleanerService;
    ServicesService servicesServiceImp;
    BookingService bookingService;
    UserService userService;
    @Autowired
    public CleanersController( BookingService bookingService,  CleanerRepository cleanerRepository,CleanerService cleanerService,    ServicesService servicesServiceImp, UserService userService) {
        this.cleanerService = cleanerService;
        this.servicesServiceImp = servicesServiceImp;
        this.userService=userService;
        this.cleanerRepository = cleanerRepository;
        this.bookingService=bookingService;
    }

    @GetMapping("/cleaners")
    private String AllCleaners(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Cleaner> cleanersList = cleanerService.findAllCleanerPagination( pageable);
        model.addAttribute("cleanersList", cleanersList);
        model.addAttribute("currentPage", page);
        return "cleaners";
    }

    @GetMapping("/cleaners/delete/{id}")
    @ResponseBody
    public String[] deleteCleanerbyId(@PathVariable("id") int id, Model model) {
//delete services from cleaner_services
        List<Booking.BookingStatus> statusList = Arrays.asList(Booking.BookingStatus.NEW, Booking.BookingStatus.IN_PROGRESS);

        // cleanerRepository.deleteCleanerServicesByCleanerId(id);
        List<Booking> bookings = bookingService.findByStatusInAndCleanerId(statusList,id);
        if(!bookings.isEmpty()){
            String arr[] = new String[2];
            arr[0] = "can't delete this cleaner, he/she has booking In progress/New";
            arr[1]= "danger";
            return  arr;
        }else{
        int result =  cleanerService.deleteById(id);
        String arr[] = new String[2];
        if(result>0){
            arr[0] = "The cleaner is deleted successfully";
            arr[1]= "success";
            return  arr;

        }else {
            arr[0] = "Can't find the cleaner";
            arr[1]= "danger";
            return  arr;

        }
        }

    }
    @GetMapping("/cleaners/edit-cleaner/{id}")
    public String editCleanerbyId(@PathVariable("id") int id, Model model) {
        Optional<Cleaner> cleaner = cleanerService.findCleanerById(id);
        model.addAttribute("cleaner",cleaner.get());
        model.addAttribute("id", id);
        List<Services> listServices =  servicesServiceImp.getAllActiveServices();
        model.addAttribute("listServices", listServices);
        return "edit-cleaner";
    }
    @PostMapping("/cleaners/update-cleaner/{id}")
    public String updateServices(@PathVariable("id") int id, @Valid @ModelAttribute("cleaner") Cleaner cleaner, BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) {
        System.out.println(cleaner.toString());
        System.out.println(cleaner.getServices().toString());
        System.out.println(cleaner.getUser().toString());
        List<Services> listServices =  servicesServiceImp.getAllActiveServices();

        if(bindingResult.hasErrors())

        {
            model.addAttribute("listServices", listServices);
            model.addAttribute("id", id);
            // log.warn("Wrong attempt");
            return "edit-cleaner";
        }

        Optional<Cleaner> existCleaner = cleanerService.findCleanerById(id);
        if (existCleaner.isPresent()) {

            if(userService.findUserByEmail(cleaner.getUser().getEmail()) != null && userService.findUserByEmail(cleaner.getUser().getEmail()).getId() != existCleaner.get().getUser().getId())
            {     model.addAttribute("listServices", listServices);
                model.addAttribute("id", id);
                model.addAttribute("duplicateEmail","Email is used in Homey" );
                return "edit-cleaner";
            }
            ModelMapper modelMapper = new ModelMapper();
            List<Booking.BookingStatus> statusList = Arrays.asList(Booking.BookingStatus.NEW, Booking.BookingStatus.IN_PROGRESS);

            UserDTO userDTO = modelMapper.map(cleaner.getUser(), UserDTO.class);
            userDTO.setRoleName("ROLE_CLEANER");
            userDTO.setId(existCleaner.get().getUser().getId());
            userDTO.setPassword(existCleaner.get().getUser().getPassword());
            int idUser =  userService.update(userDTO);
            cleaner.setUser(userService.findById(idUser).get());
            List<Integer> canChangeService = cleanerService.checkUpdatedServices(id,  cleaner.getServices());
            if(!canChangeService.isEmpty()){
                Collection<Services> newServices =  cleaner.getServices();
                for (int oldSerivceId:canChangeService) {
                    newServices.add(servicesServiceImp.getServiceById(oldSerivceId).get());
                }
                cleaner.setServices(newServices);
                redirectAttrs.addFlashAttribute("droppedServices", "Service/s that associated with currentBooking will not dropped");

            }
            boolean canChangeTime = cleanerService.updateCleanerSchedule(id ,cleaner.getStartTime(), cleaner.getHours());
            if(!canChangeTime){
                cleaner.setStartTime(existCleaner.get().getStartTime());
                cleaner.setHours(existCleaner.get().getHours());
                redirectAttrs.addFlashAttribute("changeTimeAndHours", "Cannot change schedule as there are bookings associated with this cleaner during the new working hours.");

            }
            if(!cleaner.isActive()){
                if(!(bookingService.findByStatusInAndCleanerId(statusList,id).isEmpty())){
                    cleaner.setActive(true);
                    redirectAttrs.addFlashAttribute("message", "Cleaner is updated and still active, he/she has bookings");
                    redirectAttrs.addFlashAttribute("alertType", "alert-warning");
                    cleanerRepository.save(cleaner);
                    return "redirect:/cleaners";
                }
            }
            cleanerRepository.save(cleaner);
            redirectAttrs.addFlashAttribute("message", "Cleaner is updated successfully");
            redirectAttrs.addFlashAttribute("alertType", "alert-success");
        }else{

            redirectAttrs.addFlashAttribute("message", "Cleaner cannot be found");
            redirectAttrs.addFlashAttribute("alertType", "alert-danger");
        }

        return "redirect:/cleaners";

    }





}
