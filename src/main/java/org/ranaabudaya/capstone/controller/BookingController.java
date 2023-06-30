package org.ranaabudaya.capstone.controller;

import jakarta.persistence.Access;
import org.ranaabudaya.capstone.entity.Booking;
import org.ranaabudaya.capstone.entity.Cleaner;
import org.ranaabudaya.capstone.entity.Customer;
import org.ranaabudaya.capstone.entity.User;
import org.ranaabudaya.capstone.service.BookingService;
import org.ranaabudaya.capstone.service.CleanerService;
import org.ranaabudaya.capstone.service.CustomerService;
import org.ranaabudaya.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
public class BookingController {

  BookingService bookingService ;
    UserService userService;
    CleanerService cleanerService;
    CustomerService customerService;

  @Autowired
  public BookingController(CustomerService customerService,CleanerService cleanerService,BookingService bookingService,   UserService userService){
      this.bookingService= bookingService;
      this.userService=userService;
      this.cleanerService=cleanerService;
      this.customerService=customerService;
  }



    @GetMapping("/bookings")
    public String bookingList(Model model, Principal principal) {
        // Get the logged-in user's username (which is the user ID)
        if(principal == null){

            return "redirect:/login";
        }
        String username = principal.getName();
        List<Booking> bookings;
        User user = userService.findUserByEmail(username);

        if(user.hasRole("ROLE_CLIENT")){
            System.out.println("here Client"+principal.toString());
            Customer customer=customerService.findCustomerByUserId(user.getId());
            model.addAttribute("deletedCustomer", customer.isDeleted());
            bookings = bookingService.findBookingByCustomerId(customer.getId());

        }else if(user.hasRole("ROLE_CLEANER")){
            System.out.println("here ROLE_CLEANER"+principal.toString());
            Cleaner cleaner= cleanerService.findByUserId(user.getId());
            bookings=bookingService.findBookingByCleanerId(cleaner.getId());

        }else{
            System.out.println("here admin"+principal.toString());

            bookings = bookingService.getAll();
        }

        // Add the bookings to the model
      model.addAttribute("bookings", bookings);


        return "bookings";
    }

    @GetMapping("/bookings/delete/{id}")
    @ResponseBody
    public String[] deleteBookingbyId(@PathVariable("id") int id, Model model) {
        int result =  bookingService.deleteById(id);
        String arr[] = new String[2];
        if(result>0){
            arr[0] = "The booking is deleted successfully";
            arr[1]= "success";
            return  arr;

        }else {
            arr[0] = "Can't delete the booking. It may be not found or not new booking.";
            arr[1]= "danger";
            return  arr;

        }

    }

}
