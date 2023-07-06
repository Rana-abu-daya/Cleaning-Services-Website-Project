package org.ranaabudaya.capstone.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.ranaabudaya.capstone.dto.ServicesDTO;
import org.ranaabudaya.capstone.entity.Booking;
import org.ranaabudaya.capstone.entity.Cleaner;
import org.ranaabudaya.capstone.entity.Services;
import org.ranaabudaya.capstone.repository.BookingRepository;
import org.ranaabudaya.capstone.repository.CleanerRepository;
import org.ranaabudaya.capstone.repository.ServicesRepository;
import org.ranaabudaya.capstone.service.ServicesService;
import org.ranaabudaya.capstone.service.ServicesServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/services")
public class ServicesController {

    ServicesService servicesServiceImp;
    CleanerRepository cleanerRepository;
    BookingRepository bookingRepository;
    ServicesRepository servicesRepository;
    @Autowired
    public ServicesController(ServicesRepository servicesRepository, BookingRepository bookingRepository, ServicesService servicesServiceImp, CleanerRepository cleanerRepository) {
        this.servicesServiceImp = servicesServiceImp;
        this.cleanerRepository = cleanerRepository;
        this.bookingRepository =bookingRepository;
        this.servicesRepository=servicesRepository;
    }



    @ModelAttribute("ServicesList")
    private List<Services> getServices(){
        List<Services> list =  servicesServiceImp.getAllServicesWithoutPage();
        return list;
    }
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/services")
    public String servicesListView(Model model, @RequestParam(defaultValue = "0") int page) {
            Pageable pageable = PageRequest.of(page, 5); // get 5 items per page
            Page<Services> itemPage = servicesServiceImp.getAllServices(pageable);

            model.addAttribute("data", itemPage);
            model.addAttribute("currentPage", page);
        return "services";
    }

    @GetMapping("/add-service")
    public String addService(@ModelAttribute("service") ServicesDTO serviceDTO){

        return "newService";
    }

    @PostMapping("/saveService")
    public String saveService(@Valid @ModelAttribute ("service") ServicesDTO serviceDTO, BindingResult bindingResult, RedirectAttributes redirectAttrs,Model model){
        System.out.println(serviceDTO);

        if(bindingResult.hasErrors())
        {
           log.warn("Wrong attempt add services");
            return "newService";
        }
        Services services = servicesServiceImp.getServiceByName(serviceDTO.getName());
        if(services != null ){
            model.addAttribute("ServiceName", "Service name is used");

            return "newService";
        }
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        serviceDTO.setDescription(serviceDTO.getDescription().replace("\n", "**"));
        Services service = modelMapper.map(serviceDTO, Services.class);
        servicesServiceImp.saveService(service);
        redirectAttrs.addFlashAttribute("message", "The service is added successfully");
        redirectAttrs.addFlashAttribute("alertType", "alert-success");

        log.info("service is added");
        return "redirect:/services/services";

    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public String[] deleteServicebyId(@PathVariable("id") int id) {
        List<Booking>bookings = bookingRepository.findBookingByServiceId(id);
        if(!bookings.isEmpty()){
            String arr[] = new String[2];
            arr[0] = "The deletion of the service failed. There are bookings assiociated to this service";
            arr[1]= "danger";
            return  arr;
        }
        List<Cleaner>  cleaner = cleanerRepository.findAllByServicesId(id);
        Services existService = servicesServiceImp.getServiceById(id).get();

        if (cleaner != null && cleaner.size()!=0 && existService != null) {
            for (Cleaner clean: cleaner) {
                clean.getServices().remove(existService);
                cleanerRepository.save(clean);
            }

        }
        int result =  servicesServiceImp.deleteById(id);
        String arr[] = new String[2];
    
       if(result>0){

           arr[0] = "The service is deleted successfully";
           arr[1]= "success";
           return  arr;
       }else {
           arr[0] = "The deletion of the service failed";
           arr[1]= "danger";
           return  arr;


       }
    }
    @GetMapping("/edit-service/{id}")
    public String editServicebyId(@PathVariable("id") int id, Model model) {
        Optional<Services> s = servicesServiceImp.getServiceById(id);
        model.addAttribute("service", s);
        model.addAttribute("id", id);

        return "edit-service";
    }

    @PostMapping("/updateService/{id}")
    public String updateServices(@PathVariable("id") int id, @Valid @ModelAttribute ("service") ServicesDTO serviceDTO, BindingResult bindingResult,Model model,RedirectAttributes redirectAttrs) {

        if(bindingResult.hasErrors())

        {

            model.addAttribute("id", id);
            log.warn("Wrong attempt edit service");
            return "edit-service";
        }

        Optional<Services> serv = servicesServiceImp.getServiceById(id);
        serviceDTO.setDescription(serviceDTO.getDescription().replace("\n", "**"));
        if (serv.isPresent()) {
            Services updatedService = serv.get();
            Services services = servicesServiceImp.getServiceByName(serviceDTO.getName());
            if(services != null && services.getId() != id ){
                model.addAttribute("ServiceName", "Service name is used");
                return "edit-service";
            }

            updatedService.setName(serviceDTO.getName());
            updatedService.setDescription(serviceDTO.getDescription());
            if(!serviceDTO.isActive()){
                List<Booking.BookingStatus> statusList = Arrays.asList(Booking.BookingStatus.NEW, Booking.BookingStatus.IN_PROGRESS);
                List<Booking>bookings = bookingRepository.findByStatusInAndServiceId(statusList,serv.get().getId());
                if(!bookings.isEmpty()) {
                serviceDTO.setActive(true);
                redirectAttrs.addFlashAttribute("activation", "The service can't be deactivated, there are bookings associated with it");

                }
            }
            updatedService.setActive(serviceDTO.isActive());
            updatedService.setPrice(serviceDTO.getPrice());
            servicesServiceImp.saveService(updatedService);
            redirectAttrs.addFlashAttribute("message", "The service is updated successfully");
            redirectAttrs.addFlashAttribute("alertType", "alert-success");
        }else{

            redirectAttrs.addFlashAttribute("message", "The service cannot be found");
            redirectAttrs.addFlashAttribute("alertType", "alert-danger");
        }
        log.info("edit service successfully");
        return "redirect:/services/services";

    }

}
