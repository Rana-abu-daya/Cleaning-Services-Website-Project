package org.ranaabudaya.capstone.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.ranaabudaya.capstone.dto.UserDTO;
import org.ranaabudaya.capstone.entity.Admin;
import org.ranaabudaya.capstone.entity.Booking;
import org.ranaabudaya.capstone.entity.Customer;
import org.ranaabudaya.capstone.repository.BookingRepository;
import org.ranaabudaya.capstone.repository.CustomerRepository;
import org.ranaabudaya.capstone.service.BookingService;
import org.ranaabudaya.capstone.service.CustomerService;
import org.ranaabudaya.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class CustomerController {
    // Instance variables
    CustomerRepository customerRepository;
    CustomerService customerService;
    UserService userService;
    BookingRepository bookingRepository;
    BookingService bookingService;
    // Constructor for dependency injection
    @Autowired
    public CustomerController(BookingService bookingService, BookingRepository bookingRepository,CustomerRepository customerRepository,CustomerService customerService, UserService userService){
        this.customerRepository = customerRepository;
        this.customerService = customerService;
        this.userService=userService;
        this.bookingRepository=bookingRepository;
        this.bookingService =bookingService;
    }
    // Method to get all customers with pagination
    @GetMapping("/customers")
    private String AllCustomer(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5); // get 5 items per page
        Page<Customer> customerList = customerService.getAllActivePagination(pageable);

        model.addAttribute("customerList", customerList);
        model.addAttribute("currentPage", page);

        return "customers";
    }
    //Method to get all deleted customers
    @GetMapping("/customers/deletedCustomer")
    private String AllDeletedCustomer(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5); // get 5 items per page
        Page<Customer> DeletedCustomers = customerService.getAllDeleted(pageable);

        model.addAttribute("DeletedCustomers", DeletedCustomers);
        model.addAttribute("currentPage", page);

        return "DeletedCustomers";
    }
//Method for activating the deleted customer by ID
    @GetMapping("/customers/deletedCustomer/activate/{id}")
    @ResponseBody
    public String[] activateCustomerById(@PathVariable("id") int id, Model model) {

        String arr[] = new String[2];
    if(customerService.findCustomerById(id).isPresent()){
         customerService.activateById(id);
            arr[0] = "The customer is activated successfully";
            arr[1]= "success";
            return  arr;

        } else {
            arr[0] = "The customer's activation failed.";
            arr[1]= "danger";
            return  arr;

        }

    }
    //Method for deleting customers by id
    //if there are customers related to bookings will be converted to deleted customers
    //otherwise will be deleted from the database
    @GetMapping("/customers/delete/{id}")
    @ResponseBody
    public String[] deleteCustomerById(@PathVariable("id") int id, Model model) {


        List<Booking> bookings  = bookingRepository.findByCustomerIdAndStatus(id, Booking.BookingStatus.IN_PROGRESS);
        if(!bookings.isEmpty()){
            String arr[] = new String[2];
            arr[0] = "The customer's deletion failed. There are bookings in progress";
            arr[1]= "danger";
            return  arr;
        }
        List<Booking> newBookings  = bookingRepository.findByCustomerIdAndStatus(id, Booking.BookingStatus.NEW);
        for (Booking booking: newBookings) {
            booking.setStatus(Booking.BookingStatus.CANCELLED);
            bookingService.update(booking);
        }

        int result =  customerService.deleteById(id);
        String arr[] = new String[2];
        if(result>0){
            arr[0] = "The customer is deleted successfully";
            arr[1]= "success";
            return  arr;

        }else {
            arr[0] = "The customer's deletion failed.";
            arr[1]= "danger";
            return  arr;

        }

    }
    // Method to get a customer by their ID for editing
    @GetMapping("/customers/edit-customer/{id}")
    public String editAdminbyId(@PathVariable("id") int id, Model model) {
        Optional<Customer> customer = customerService.findCustomerById(id);
        model.addAttribute("customer",customer);
        model.addAttribute("id", id);
        return "edit-customer";
    }
    // Method to process the edit
    @PostMapping("/customers/updateCustomer/{id}")
    public String updateServices(@PathVariable("id") int id, @Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) {
        System.out.println(customer.toString());
        System.out.println(customer.getUser().toString());
        if(bindingResult.hasErrors())

        {

            model.addAttribute("id", id);
            log.warn("Wrong attempt edit customer");
            return "edit-customer";
        }

        Optional<Customer> customer1 = customerService.findCustomerById(id);
        if (customer1.isPresent()) {

            if(userService.findUserByEmail(customer.getUser().getEmail()) != null && userService.findUserByEmail(customer.getUser().getEmail()).getId() != customer1.get().getUser().getId())
            {
                model.addAttribute("duplicateEmail","Email is used in Homey" );
                return "edit-customer";
            }
            ModelMapper modelMapper = new ModelMapper();

            UserDTO userDTO = modelMapper.map(customer.getUser(), UserDTO.class);
            userDTO.setRoleName("ROLE_CLIENT");
            userDTO.setId(customer1.get().getUser().getId());
            userDTO.setPassword(customer1.get().getUser().getPassword());
            userDTO.setPhoto(customer1.get().getUser().getPhoto());
            int idUser =  userService.update(userDTO);
            customer.setUser(userService.findById(idUser).get());

            customerRepository.save(customer);
            redirectAttrs.addFlashAttribute("message", "customer is updated successfully");
            redirectAttrs.addFlashAttribute("alertType", "alert-success");
        }else{

            redirectAttrs.addFlashAttribute("message", " customer cannot be found");
            redirectAttrs.addFlashAttribute("alertType", "alert-danger");
        }
        log.info("edit customer went successfuly");
        return "redirect:/customers";

    }



}
