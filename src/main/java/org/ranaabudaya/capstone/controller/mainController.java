package org.ranaabudaya.capstone.controller;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ranaabudaya.capstone.dto.BookingDTO;
import org.ranaabudaya.capstone.dto.CustomerDTO;
import org.ranaabudaya.capstone.dto.MessageDTO;
import org.ranaabudaya.capstone.entity.*;
import org.ranaabudaya.capstone.helper.CustomerformWrapper;
import org.ranaabudaya.capstone.repository.ReviewRepository;
import org.ranaabudaya.capstone.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class mainController {
    // Instance variables
    ServicesServiceImp servicesServiceImp;
    CleanerService cleanerService;
    UserService userService;
    BookingService bookingService;
    CustomerService customerService;
    ReviewRepository reviewRepository;
    // Constructor for dependency injection
    @Autowired
    public mainController(ReviewRepository reviewRepository, CustomerService customerService, ServicesServiceImp servicesServiceImp, CleanerService cleanerService,
                          UserService userService, BookingService bookingService) {
        this.servicesServiceImp = servicesServiceImp;
        this.cleanerService = cleanerService;
        this.userService = userService;
        this.bookingService = bookingService;
        this.customerService = customerService;
        this.reviewRepository = reviewRepository;
    }

    @Autowired
    ReviewService reviewService;

    //this list of list for slider view at home page ,, separate them each in one list
    @ModelAttribute("ServicesList")
    private List<List<Services>> getServices() {
        List<Services> list = servicesServiceImp.getActiveServies();
        List<List<Services>> finals = new ArrayList<>();
        int sublistSize = 3;

        for (int i = 0; i < list.size(); i += sublistSize) {
            int endIndex = Math.min(i + sublistSize, list.size());
            List<Services> sublist = list.subList(i, endIndex);
            finals.add(sublist);
        }
        return finals;
    }

    //this is normal list of services
    @ModelAttribute("myServicesList")
    private List<Services> getMyServices() {
        List<Services> list = servicesServiceImp.getActiveServies();
        return list;
    }
//display the home page
    @GetMapping(value = {"/home", "/homey", "/index"})
    public String home(Model model) {
        List<Review> reviews = reviewService.findTop3ByOrderByRatingValueDesc();
        model.addAttribute("reviews", reviews);
        //System.out.println(reviews);
        model.addAttribute("messageDTO", new MessageDTO());
        return "index";
    }
//the dashboard page
    @GetMapping(value = {"/dashboard"})
    public String dashboard(HttpSession session, Model model, Authentication authentication) {
        String savedBooking1 = (String) session.getAttribute("savedBooking");
        String savedBookingalertType2 = (String) session.getAttribute("savedBookingalertType");
        System.out.println(savedBooking1 + savedBookingalertType2);
        if (savedBooking1 != null && savedBookingalertType2 != null) {
            session.removeAttribute("savedBooking");
            session.removeAttribute("savedBookingalertType");
            model.addAttribute("savedBooking", savedBooking1);
            model.addAttribute("savedBookingalertType", savedBookingalertType2);
        }

        if (authentication != null) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals("ROLE_CLEANER")) {
                    String activation = (String) session.getAttribute("activation");
                    System.out.println(activation);
                    if (activation != null) {
                        session.removeAttribute("activation");
                        model.addAttribute("activation", activation);
                    }
                    String message = (String) session.getAttribute("message");
                    String alertType = (String) session.getAttribute("alertType");
                    if (message != null && alertType != null) {
                        session.removeAttribute("message");
                        session.removeAttribute("alertType");
                        model.addAttribute("message", message);
                        model.addAttribute("alertType", alertType);
                    }
                }

            }
        }
        return "dashboard";
    }
//the dashboard data
    @GetMapping(value = {"/dashboard/dash"})
    public String dash(Model model) {
        double total = bookingService.findTotalMony(Booking.BookingStatus.SUCCESS);
        model.addAttribute("total", total);
        int totalCustomer = customerService.getAllActive().size();
        model.addAttribute("totalCustomer", totalCustomer);
        int newCleaners = cleanerService.findByIsActiveAndIsNew(false, true).size();
        model.addAttribute("newCleaners", newCleaners);
        int Allcleaners = cleanerService.getAll().size() - newCleaners;
        model.addAttribute("totalCleaners", Allcleaners);
        List<Cleaner> cleaners = cleanerService.findTopCleaner();
        model.addAttribute("topCleaners", cleaners);


        return "dash";
    }
// form to add new booking
    @GetMapping(value = {"/booking"})
    public String booking(Model model, @RequestParam(defaultValue = "0") int id) {
        model.addAttribute("id", id);
        model.addAttribute("Booking", new BookingDTO());

        return "Booking";
    }

    //create the div of the html view by getting the availble cleaner in this time/date slot and serviceId
    @GetMapping(value = {"/booking/cleaners/{serviceId}/{startTime}/{hours}/{bookingDate}"})
    public String bookingCleaners(@PathVariable("serviceId") int serviceId,
                                  @PathVariable("startTime") String startTime,
                                  @PathVariable("hours") int hours, @PathVariable("bookingDate") String bookingDate,
                                  Model model) {
        LocalDate bookingDate1 = LocalDate.parse(bookingDate);
        List<Cleaner> cleanersList = cleanerService.findAvailableCleanersForServiceAndTime(startTime, hours, bookingDate1, serviceId);
        for (Cleaner cleaner : cleanersList) {
            List<Review> reviews = reviewRepository.findByBookingCleanerId(cleaner.getId());
            double averageRating = reviews.stream().mapToInt(Review::getRatingValue).average().orElse(0.0);
            cleaner.setAverageRating(averageRating);
        }

        model.addAttribute("cleanersList", cleanersList);
        //System.out.println(cleanersList);
        return "CleanerDivForBooking";
    }

    //process the addtion of the booking
    //if there is no logged in customer , will save the booking into the session
    @PostMapping("/bookings/add-booking")
    public String newBooking(@Valid @ModelAttribute("Booking") BookingDTO Booking, BindingResult bindingResult
            , Model model, RedirectAttributes redirectAttrs, HttpSession session) {
        System.out.println(Booking.toString());
        if (bindingResult.hasErrors()) {
            log.warn("Wrong attempt to add booking");
            return "Booking";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(SecurityContextHolder.getContext().getAuthentication() + "ROLE_ANONYMOUS");
        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ANONYMOUS"))) {

            // User is logged in
            //bookingService.saveBooking(booking);
            // return "booking-success";
            boolean saved = false;
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals("ROLE_CLIENT")) {
                    System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
                    String email = ((UserDetails) authentication.getPrincipal()).getUsername();
                    User user = userService.findUserByEmail(email);
                    Booking.setCustomerId(user.getId());
                    Customer customer = customerService.findCustomerByUserId(user.getId());
                    if (customer.isDeleted()) {
                        session.setAttribute("savedBooking", "Booking is not created, you are inactive");
                        session.setAttribute("savedBookingalertType", "alert-warning");
                        return "redirect:/dashboard";
                    } else {
                        bookingService.create(Booking);
                        saved = true;
                        session.setAttribute("savedBooking", "Booking is saved successfully");
                        session.setAttribute("savedBookingalertType", "alert-success");
                        log.info("booking is created");
                    }
                }
            }

            if (!saved) {
                System.out.println(saved + "saaaved");
                session.setAttribute("savedBooking", "Booking is not created, you are not customer");
                session.setAttribute("savedBookingalertType", "alert-warning");

            }
            System.out.println("user is  login");
        } else {
            // User is not logged in
            System.out.println("user is not login");
            session.setAttribute("pendingBooking", Booking);
            return "redirect:/login";
        }

        return "redirect:/dashboard";
    }

    //process the login
    //if the logged in user is customer and there is booking in the session will be created
    @RequestMapping("/login-process")
    public String handleSuccessfulLogin(HttpSession session, Authentication authentication) {
        System.out.println(session.getAttributeNames());

        // Retrieve the pending booking from session or cookie
        if (authentication != null) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals("ROLE_CLIENT")) {
                    BookingDTO pendingBooking = (BookingDTO) session.getAttribute("pendingBooking");
                    if (pendingBooking != null) {
                        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
                        User user = userService.findUserByEmail(email);
                        pendingBooking.setCustomerId(user.getId());
                        Customer customer = customerService.findCustomerByUserId(user.getId());
                        if (customer.isDeleted()) {
                            session.setAttribute("savedBooking", "Booking can not be created, you are inactive");
                            session.setAttribute("savedBookingalertType", "alert-warning");
                            return "redirect:/dashboard";
                        }
                        bookingService.create(pendingBooking);
                        session.removeAttribute("pendingBooking");
                        session.setAttribute("savedBooking", "Booking is saved successfully");
                        session.setAttribute("savedBookingalertType", "alert-success");

                    }

                }
            }
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals("ROLE_CLEANER")) {
                    String email = ((UserDetails) authentication.getPrincipal()).getUsername();
                    User user = userService.findUserByEmail(email);
                    Cleaner cleaner = cleanerService.findByUserId(user.getId());
                    if (!cleaner.isActive()) {
                        session.setAttribute("activation", "You still inactive");
                    }

                } else if (authority.getAuthority().equals("ROLE_CLIENT")) {
                    String email = ((UserDetails) authentication.getPrincipal()).getUsername();
                    User user = userService.findUserByEmail(email);
                    Customer customer = customerService.findCustomerByUserId(user.getId());
                    if (customer.isDeleted()) {
                        session.setAttribute("activation", "You still inactive");
                    }
                }
            }
        }


        return "redirect:/dashboard";

    }
}
