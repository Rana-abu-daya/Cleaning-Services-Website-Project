package org.ranaabudaya.capstone.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ranaabudaya.capstone.dto.MessageDTO;
import org.ranaabudaya.capstone.dto.ReviewDTO;
import org.ranaabudaya.capstone.dto.ServicesDTO;
import org.ranaabudaya.capstone.entity.*;
import org.ranaabudaya.capstone.repository.BookingRepository;
import org.ranaabudaya.capstone.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.print.Book;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class ReviewController {

    BookingService bookingService;
    ReviewService reviewService;
    UserService userService;
    CustomerService customerService;
    CleanerService cleanerService;
    @Autowired
    MessageService messageService;
    @Autowired
    public ReviewController(CleanerService cleanerService,CustomerService customerService,UserService userService ,BookingService bookingService,ReviewService reviewService){
        this.bookingService =bookingService;
        this.reviewService=reviewService;
        this.userService = userService;
        this.customerService =customerService;
        this.cleanerService=cleanerService;
    }
    @GetMapping("/reviews")
    public String reviews(Model model, Principal principal,@RequestParam(defaultValue = "0") int page) {
        if(principal == null){

            return "redirect:/login";
        }
        String username = principal.getName();
        User user = userService.findUserByEmail(username);
        List<Booking> bookings;
        if(user.hasRole("ROLE_CLIENT")){
            System.out.println("here Client"+principal.toString());
            Customer customer=customerService.findCustomerByUserId(user.getId());
            model.addAttribute("deletedCustomer", customer.isDeleted());
            List<Booking.BookingStatus> statuses = new ArrayList<>();
            statuses.add(Booking.BookingStatus.SUCCESS);
            bookings = bookingService.findByStatusInAndCustomerId(statuses,customer.getId());

            Pageable pageable = PageRequest.of(page, 5);
            int start = (int)pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), bookings.size());
            if(page != 0 && page>=start && page<=end){
                model.addAttribute("currentPage", page);
            }else{
                model.addAttribute("currentPage", 0);
            }
            Page<Booking> list= new PageImpl<>(bookings.subList(start, end), pageable, bookings.size());;
            // Add the bookings to the model
            model.addAttribute("bookings", list);


        } else if(user.hasRole("ROLE_CLEANER")){
            Cleaner cleaner=cleanerService.findByUserId(user.getId());
            List<Booking.BookingStatus> statuses = new ArrayList<>();
            statuses.add(Booking.BookingStatus.SUCCESS);
            bookings = bookingService.findByStatusInAndCleanerId(statuses,cleaner.getId());
            Pageable pageable = PageRequest.of(page, 5);
            int start = (int)pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), bookings.size());
            if(page != 0 && page>=start && page<=end){
                model.addAttribute("currentPage", page);
            }else{
                model.addAttribute("currentPage", 0);
            }
            Page<Booking> list= new PageImpl<>(bookings.subList(start, end), pageable, bookings.size());;
            // Add the bookings to the model
            model.addAttribute("bookings", list);
        }else{
            List<Booking.BookingStatus> statuses = new ArrayList<>();
            bookings = bookingService.findBookingByStatus(Booking.BookingStatus.SUCCESS);
            Pageable pageable = PageRequest.of(page, 5);
            int start = (int)pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), bookings.size());
            if(page != 0 && page>=start && page<=end){
                model.addAttribute("currentPage", page);
            }else{
                model.addAttribute("currentPage", 0);
            }
            Page<Booking> list= new PageImpl<>(bookings.subList(start, end), pageable, bookings.size());;
            // Add the bookings to the model
            model.addAttribute("bookings", list);
        }

        model.addAttribute("reviewDTO", new ReviewDTO());

        return "Booking-Reviews";
    }

    @GetMapping("/rating/{id}")
    public String rating(@PathVariable("id") int id, Model model) {
        Booking booking = bookingService.findBookingById(id).get();
        model.addAttribute("booking", booking);
        model.addAttribute("id", id);
        return "review";
    }


    @PostMapping("/message/add")
    public String messageadd(@Valid @ModelAttribute ("messageDTO") MessageDTO messageDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) {

        if(bindingResult.hasErrors())

        {


            log.warn("Wrong attempt");
            return "index";
        }


            messageService.create(messageDTO);


        return "redirect:/index";

    }
    @PostMapping("/rating/add/{id}")
    @ResponseBody
    public String[] addRating(@PathVariable("id") int id, @Valid @ModelAttribute("review") ReviewDTO reviewDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) {
         if(bookingService.findBookingById(id).get().getStatus().equals(Booking.BookingStatus.SUCCESS)) {
             if ((reviewDTO.getComment() != null || reviewDTO.getComment().isEmpty())
                    && reviewDTO.getRatingValue() != 0) {
                 if (reviewService.findBookingById(id) != null) {
                     //reviewService.deleteByBookingId(id);
                     reviewDTO.setBookingId(id);
                     System.out.println(reviewDTO.getRatingValue() + reviewDTO.getBookingId()+reviewDTO.getComment());
                     reviewService.create(reviewDTO);
                     String arr[] = new String[4];
                     arr[0] = "Thanks for your Review, your old review was deleted";
                     arr[1] = "success";
                     StringBuilder html = new StringBuilder();
                     html.append("Reviews:<span class='text-dark font-weight-bold ms-sm-2'>")
                             .append(reviewDTO.getComment())
                             .append("</span>")
                             .append("<div class='stars'>");

                     for (int i = 1; i <= 5; i++) {
                         html.append("<span class='fa ")
                                 .append(reviewDTO.getRatingValue() >= i ? "fa-star" : "fa-star-o")
                                 .append("'></span>");
                     }

                     html.append("</div>");
                     arr[2]=html.toString();
                     arr[3]=id+"";
                     return arr;
                 } else {
                     reviewDTO.setBookingId(id);
                     System.out.println(reviewDTO.getRatingValue() + reviewDTO.getBookingId()+reviewDTO.getComment());
                     reviewService.create(reviewDTO);
                     String arr[] = new String[4];
                     arr[0] = "Thanks for your Review";
                     arr[1] = "success";
                     StringBuilder html = new StringBuilder();
                     html.append("Reviews: <span class='text-dark font-weight-bold ms-sm-2'>")
                             .append(reviewDTO.getComment())
                             .append("</span>")
                             .append("<div class='stars'>");

                     for (int i = 1; i <= 5; i++) {
                         html.append("<span class='fa ")
                                 .append(reviewDTO.getRatingValue() >= i ? "fa-star" : "fa-star-o")
                                 .append("'></span>");
                     }

                     html.append("</div>");
                     arr[2]=html.toString();
                     arr[3]=id+"";
                     return arr;
                 }
             } else {

                 String arr[] = new String[2];
                 arr[0] = "No review submitted";
                 arr[1] = "danger";
                 return arr;
             }
         }else{
             String arr[] = new String[2];
             arr[0] = "you can't review this booking, it is not completed yet.";
             arr[1] = "danger";
             return arr;
         }


    }



}
