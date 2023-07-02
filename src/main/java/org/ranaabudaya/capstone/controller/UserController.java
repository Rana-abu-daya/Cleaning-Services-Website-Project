package org.ranaabudaya.capstone.controller;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ranaabudaya.capstone.dto.AdminDTO;
import org.ranaabudaya.capstone.dto.BookingDTO;
import org.ranaabudaya.capstone.dto.CleanerDTO;
import org.ranaabudaya.capstone.dto.CustomerDTO;
import org.ranaabudaya.capstone.entity.Services;
import org.ranaabudaya.capstone.entity.User;
import org.ranaabudaya.capstone.helper.AdminformWrapper;
import org.ranaabudaya.capstone.helper.CustomerformWrapper;
import org.ranaabudaya.capstone.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import  org.ranaabudaya.capstone.helper.FormWrapper;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


/**
 * @author Igor Adulyan
 */
@Controller
@Slf4j
public class UserController {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }


    private UserService userService;
    private ServicesService servicesServiceImp;
    private  CleanerService cleanerService;
    private AdminService adminService;
    private CustomerService customerService;
    private BookingService bookingService;
    private FileService fileService;
    @Autowired
    public UserController(FileService fileService,BookingService bookingService,UserService userDetailsService, ServicesService servicesServiceImp , CleanerService cleanerService,
                          AdminService adminService,CustomerService customerService) {
        this.userService = userDetailsService;
        this.servicesServiceImp = servicesServiceImp;
        this.cleanerService = cleanerService;
        this.adminService=  adminService;
        this.customerService  = customerService;
        this.bookingService=bookingService;
        this.fileService=fileService;
    }

    @GetMapping("/")
    private String redirectToLogin()
    {
        return "redirect:/login";
    }

//Cleaner sign up
    @GetMapping("/cleaners/sign-up")
    public String signUp(Model model)
    {
        model.addAttribute("formWrapper", new FormWrapper());
        List<Services> list =  servicesServiceImp.getAllActiveServices();
        model.addAttribute("servicesList",list );
//        model.addAttribute("userDto", new UserDTO());
//        model.addAttribute("cleanerDTO", new CleanerDTO());
        return "sign-up-cleaner";
    }

    @PostMapping("/cleaners/signup-process")
    public String signupProcess(@Valid @ModelAttribute ("formWrapper") FormWrapper formWrapper, BindingResult bindingResult
    ,  Model model, RedirectAttributes redirectAttrs, HttpSession session)
    {
        if(bindingResult.hasErrors()  )
        {
            List<Services> list =  servicesServiceImp.getAllActiveServices();
            model.addAttribute("servicesList",list );

           // log.warn("Wrong attempt");
            return "sign-up-cleaner";
        }
        System.out.println(formWrapper.getUserDTO());
        System.out.println(formWrapper.getCleanerDTO());
        //System.out.println(Arrays.toString(services));

        if(userService.findUserByEmail(formWrapper.getUserDTO().getEmail()) != null)
        {  List<Services> list =  servicesServiceImp.getAllActiveServices();
            model.addAttribute("servicesList",list );
            model.addAttribute("duplicateEmail","Email is used in Homey" );
            return "sign-up-cleaner";
        }else {

            CleanerDTO cleanerDTO = formWrapper.getCleanerDTO();
            formWrapper.getUserDTO().setRoleName("ROLE_CLEANER");
            System.out.println(formWrapper.getUserDTO().getFile().isEmpty());
            if(formWrapper.getUserDTO().getFile() != null && !formWrapper.getUserDTO().getFile().isEmpty()) {
                System.out.println(formWrapper.getUserDTO().getFile().getName());
                fileService.uploadFile(formWrapper.getUserDTO().getFile());
                formWrapper.getUserDTO().setPhoto(formWrapper.getUserDTO().getFile().getOriginalFilename());
            }
            int userId = userService.create(formWrapper.getUserDTO());
           // System.out.println(userId + "Rana");
            cleanerDTO.setUserId(userId);
            cleanerDTO.setActive(false);
            cleanerDTO.setNew(true);
            cleanerService.create(cleanerDTO);
            session.setAttribute("message", "Welcome to Homey.. ");
            session.setAttribute("alertType", "alert-success");
            session.setAttribute("activation", "You are inactive, we will review your resume soon");

            //userService.create(userDTO);
            return "redirect:/login";
        }
    }

//Add Admin
@GetMapping("/admins/sign-up")
public String addAdmin(Model model)
{
    model.addAttribute("AdminForm", new AdminformWrapper());
//        model.addAttribute("userDto", new UserDTO());
//        model.addAttribute("cleanerDTO", new CleanerDTO());
    return "sign-up-admin";
}

    @PostMapping("/admins/signup-process")
    public String signupProcessAdmin(@Valid @ModelAttribute ("AdminForm") AdminformWrapper AdminForm, BindingResult bindingResult
            ,  Model model, RedirectAttributes redirectAttrs)
    {
        if(bindingResult.hasErrors()  )
        {


            // log.warn("Wrong attempt to add admin");
            return "sign-up-admin";
        }
        System.out.println(AdminForm.getUserDTO());
        System.out.println(AdminForm.getAdminDTO());

        if(userService.findUserByEmail(AdminForm.getUserDTO().getEmail()) != null)
        {
            model.addAttribute("duplicateEmail","Email is used in Homey" );
            return "sign-up-admin";
        }else {

            AdminDTO adminDTO = AdminForm.getAdminDTO() != null ? AdminForm.getAdminDTO() : new AdminDTO();
            AdminForm.getUserDTO().setRoleName("ROLE_ADMIN");
            System.out.println(AdminForm.getUserDTO().getFile().isEmpty());
            if(AdminForm.getUserDTO().getFile() != null && !AdminForm.getUserDTO().getFile().isEmpty()) {
                System.out.println(AdminForm.getUserDTO().getFile().getName());
                fileService.uploadFile(AdminForm.getUserDTO().getFile());
                AdminForm.getUserDTO().setPhoto(AdminForm.getUserDTO().getFile().getOriginalFilename());
            }
            int userId = userService.create(AdminForm.getUserDTO());
            // System.out.println(userId + "Rana");
            adminDTO.setUserId(userId);

            adminService.create(adminDTO);
            redirectAttrs.addFlashAttribute("message", "New Admin is added successfully.. ");
            redirectAttrs.addFlashAttribute("alertType", "alert-success");

            //userService.create(userDTO);
            return "redirect:/admins";
        }
    }

    //Add Client
    @GetMapping("/customers/sign-up")
    public String addCustomer(Model model)
    {
        model.addAttribute("Customerform", new CustomerformWrapper());

        return "sign-up-customer";
    }
    @GetMapping("/customers/admin/sign-up")
    public String addCustomerAdmin(Model model)
    {
        model.addAttribute("Customerform", new CustomerformWrapper());

        return "newCustomer";
    }
    @PostMapping("/customers/admin/signup-process")
    public String signupProcessCustomerByAdmin(@Valid @ModelAttribute ("Customerform") CustomerformWrapper Customerform, BindingResult bindingResult
            , Model model, RedirectAttributes redirectAttrs) throws Exception
    {
        if(bindingResult.hasErrors()  )
        {
            // log.warn("Wrong attempt to add admin");
            return "newCustomer";
        }

        System.out.println(Customerform.getUserDTO());
        System.out.println(Customerform.getCustomerDTO());

        if(userService.findUserByEmail(Customerform.getUserDTO().getEmail()) != null)
        {
            model.addAttribute("duplicateEmail","Email is used in Homey" );
            return "newCustomer";
        }else {

            CustomerDTO customerDTO = Customerform.getCustomerDTO() != null ? Customerform.getCustomerDTO() : new CustomerDTO();
            Customerform.getUserDTO().setRoleName("ROLE_CLIENT");
            System.out.println(Customerform.getUserDTO().getFile().isEmpty());
            if(Customerform.getUserDTO().getFile() != null && !Customerform.getUserDTO().getFile().isEmpty()) {
                fileService.uploadFile(Customerform.getUserDTO().getFile());
                Customerform.getUserDTO().setPhoto(Customerform.getUserDTO().getFile().getOriginalFilename());
            }
            int userId = userService.create(Customerform.getUserDTO());
            customerDTO.setUserId(userId);

            customerService.create(customerDTO);
            redirectAttrs.addFlashAttribute("message", "New Customer is Added successfully ");
            redirectAttrs.addFlashAttribute("alertType", "alert-success");
            return "redirect:/customers";
        }
    }

    @PostMapping("/customers/signup-process")
    public String signupProcessCustomer(@Valid @ModelAttribute ("Customerform") CustomerformWrapper Customerform, BindingResult bindingResult
            , Model model, RedirectAttributes redirectAttrs, HttpSession session)
    {
        if(bindingResult.hasErrors()  )
        {
            // log.warn("Wrong attempt to add admin");
            return "sign-up-customer";
        }
        System.out.println(Customerform.getUserDTO());
        System.out.println(Customerform.getCustomerDTO());

        if(userService.findUserByEmail(Customerform.getUserDTO().getEmail()) != null)
        {
            model.addAttribute("duplicateEmail","Email is used in Homey" );
            return "sign-up-customer";
        }else {

            CustomerDTO customerDTO = Customerform.getCustomerDTO() != null ? Customerform.getCustomerDTO() : new CustomerDTO();
            Customerform.getUserDTO().setRoleName("ROLE_CLIENT");
            System.out.println(Customerform.getUserDTO().getFile().isEmpty());
            if(Customerform.getUserDTO().getFile() != null && !Customerform.getUserDTO().getFile().isEmpty()) {
                fileService.uploadFile(Customerform.getUserDTO().getFile());
                Customerform.getUserDTO().setPhoto(Customerform.getUserDTO().getFile().getOriginalFilename());
            }


            int userId = userService.create(Customerform.getUserDTO());

            customerDTO.setUserId(userId);

            customerService.create(customerDTO);
            session.setAttribute("message", "Welcome to Homey.. Enjoy our services ");
            session.setAttribute("alertType", "alert-success");

            BookingDTO pendingBooking = (BookingDTO) session.getAttribute("pendingBooking");
            if (pendingBooking != null) {

                pendingBooking.setCustomerId(userId);

                bookingService.create(pendingBooking);
                session.removeAttribute("pendingBooking");
                session.setAttribute("savedBooking", "Booking is saved successfully");
                session.setAttribute("savedBookingalertType", "alert-success");

            }
            return "redirect:/login";
        }
    }
    @GetMapping("/login")
    public String getLoginPage(@RequestParam(value = "error", required = false) String error,
    Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password");
        }
        log.info("Login page displayed");
        return "login";
    }




}
