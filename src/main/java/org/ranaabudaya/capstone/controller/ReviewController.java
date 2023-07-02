package org.ranaabudaya.capstone.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ranaabudaya.capstone.dto.ReviewDTO;
import org.ranaabudaya.capstone.dto.ServicesDTO;
import org.ranaabudaya.capstone.entity.Booking;
import org.ranaabudaya.capstone.entity.Services;
import org.ranaabudaya.capstone.repository.BookingRepository;
import org.ranaabudaya.capstone.service.BookingService;
import org.ranaabudaya.capstone.service.CustomerService;
import org.ranaabudaya.capstone.service.ReviewService;
import org.ranaabudaya.capstone.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class ReviewController {

    BookingService bookingService;
    ReviewService reviewService;
    @Autowired
    public ReviewController(BookingService bookingService,ReviewService reviewService){
        this.bookingService =bookingService;
        this.reviewService=reviewService;
    }

    @GetMapping("/rating/{id}")
    public String rating(@PathVariable("id") int id, Model model) {
        Booking booking = bookingService.findBookingById(id).get();
        model.addAttribute("booking", booking);
        model.addAttribute("id", id);
        return "review";
    }
    @PostMapping("/rating/add/{id}")
    @ResponseBody
    public String[] addRating(@PathVariable("id") int id, @Valid @ModelAttribute("review") ReviewDTO reviewDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) {
         if(!bookingService.findBookingById(id).get().getStatus().equals(Booking.BookingStatus.SUCCESS)) {
             if (reviewDTO.getComment() != null || reviewDTO.getComment().isEmpty()
                     || reviewDTO.getRatingValue() != 0) {
                 if (reviewService.findBookingById(id) != null) {
                     reviewService.deleteByBookingId(id);
                     reviewService.create(reviewDTO);
                     String arr[] = new String[2];
                     arr[0] = "Thanks for your Review, your old review was deleted";
                     arr[1] = "success";
                     return arr;
                 } else {
                     reviewService.create(reviewDTO);
                     String arr[] = new String[2];
                     arr[0] = "Thanks for your Review";
                     arr[1] = "success";
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
