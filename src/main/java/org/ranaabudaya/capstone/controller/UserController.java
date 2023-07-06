package org.ranaabudaya.capstone.controller;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.ranaabudaya.capstone.dto.*;
import org.ranaabudaya.capstone.entity.*;
import org.ranaabudaya.capstone.helper.AdminformWrapper;
import org.ranaabudaya.capstone.helper.CustomerformWrapper;
import org.ranaabudaya.capstone.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import  org.ranaabudaya.capstone.helper.FormWrapper;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


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
    private BCryptPasswordEncoder encoder;
    // Constructor for dependency injection
    @Autowired
    public UserController(BCryptPasswordEncoder encoder,FileService fileService,BookingService bookingService,UserService userDetailsService, ServicesService servicesServiceImp , CleanerService cleanerService,
                          AdminService adminService,CustomerService customerService) {
        this.userService = userDetailsService;
        this.servicesServiceImp = servicesServiceImp;
        this.cleanerService = cleanerService;
        this.adminService=  adminService;
        this.customerService  = customerService;
        this.bookingService=bookingService;
        this.fileService=fileService;
        this.encoder =encoder;
    }
//login
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
//process the sign up for cleaners
    @PostMapping("/cleaners/signup-process")
    public String signupProcess(@Valid @ModelAttribute ("formWrapper") FormWrapper formWrapper, BindingResult bindingResult
    ,  Model model, RedirectAttributes redirectAttrs, HttpSession session) throws IOException {
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
        //upload resume
        if (formWrapper.getCleanerDTO().getCv().isEmpty()|| formWrapper.getCleanerDTO().getCv() == null) {
            List<Services> list =  servicesServiceImp.getAllActiveServices();
            model.addAttribute("servicesList",list );
            model.addAttribute("resume","Resume is required" );
            return "sign-up-cleaner";
        }
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



            // normalize the file path
            String fileName = StringUtils.cleanPath(cleanerDTO.getCv().getOriginalFilename());
            log.debug("File name {} " + fileName);
           String file_name =  UUID.randomUUID().toString()+".pdf";
            fileService.encryptPDFFile("src/main/resources/resume", fileName, cleanerDTO.getCv(), file_name);
            cleanerDTO.setResume(file_name);
            /////// end upload resume
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
    //process the addition of the Admin
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

    //Add Client by the client
    @GetMapping("/customers/sign-up")
    public String addCustomer(Model model)
    {
        model.addAttribute("Customerform", new CustomerformWrapper());

        return "sign-up-customer";
    }
    //Add Client by the admin
    @GetMapping("/customers/admin/sign-up")
    public String addCustomerAdmin(Model model)
    {
        model.addAttribute("Customerform", new CustomerformWrapper());

        return "newCustomer";
    }
    //process the addition of the client by admin
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
//process the client sign up
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
//change the password form
    @GetMapping("/profile/changePassword")
    private String changePassword(Model model, Principal principal){
        if(principal == null){

            return "redirect:/login";
        }
        User user = userService.findUserByEmail(principal.getName());
        model.addAttribute("user",user);

        return "changePassword";

    }
    //process the change password
    @PostMapping("/profile/changePassword/update")
    private String changePasswordUpdate(@RequestParam("oldPassword") String oldPassword,
                                        @RequestParam("newPassword") String newPassword,@RequestParam("matchingPassword") String matchingPassword,
                                        Principal principal,
                                        RedirectAttributes redirectAttributes,Model model){
        if(oldPassword != null && !oldPassword.isEmpty() && newPassword != null && !newPassword.isEmpty()
        && matchingPassword != null && !matchingPassword.isEmpty()) {
            String username = principal.getName();
            if(matchingPassword.equals(newPassword)) {
                String result = this.changePassword(username, oldPassword, newPassword);

                if (result.equals("success")) {
                    redirectAttributes.addFlashAttribute("message", "Password changed successfully!");
                    redirectAttributes.addFlashAttribute("alertType", "alert-success");

                } else {
                    redirectAttributes.addFlashAttribute("message", result);
                    redirectAttributes.addFlashAttribute("alertType", "alert-danger");
                    return "redirect:/profile/changePassword";
                }
            }else{
                User user = userService.findUserByEmail(principal.getName());
                model.addAttribute("user",user);
                model.addAttribute("newPasswordError", "The new password fields must match");
                return "changePassword";

            }
        }else{
            redirectAttributes.addFlashAttribute("message", "All Fields are required");
            redirectAttributes.addFlashAttribute("alertType", "alert-danger");
            return "redirect:/profile/changePassword";
        }


        return "redirect:/profile";

    }
    //helper for changing password
    public String changePassword(String username, String oldPassword, String newPassword) {
        User user = userService.findUserByEmail(username);
        if (user == null) {
            return "User not found";
        }
        if (!encoder.matches(oldPassword, user.getPassword())) {
            return "Old password is incorrect";
        }

        userService.changePassword(newPassword,user);
        return "success";
    }

    //get the profile of the logged in user
@GetMapping("/profile")
    private String getProfile(Model model, Principal principal){
    if(principal == null){

        return "redirect:/login";
    }

    String username = principal.getName();
    System.out.println(username);
    System.out.println(principal);
    User user = userService.findUserByEmail(username);
    user.setPassword("1234");
    user.setMatchingPassword("1234");
    model.addAttribute("user",user);
    return "profile";

}
//update the profile changes
    @PostMapping("/profile/update")
    private String setProfile(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult,Principal principal ,Model model, RedirectAttributes redirectAttrs){
        System.out.println("here1");
        String username = principal.getName();
        System.out.println(username);
        System.out.println(bindingResult.hasErrors());
        if(bindingResult.hasErrors()){
            System.out.println("here2");
            return "profile";
        }
        System.out.println("here3");


        User existUser = userService.findUserByEmail(username);
        if(userService.findUserByEmail(userDTO.getEmail()) != null && userService.findUserByEmail(userDTO.getEmail()).getId() != existUser.getId())
        {
            model.addAttribute("duplicateEmail","Email is used in Homey" );
            return "profile";
        }
        if(!existUser.getEmail().equals(userDTO.getEmail())){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            List<GrantedAuthority> authorities = new ArrayList<>(auth.getAuthorities()); // copy the authorities
            UserDetails newUserDetails = new org.springframework.security.core.userdetails.User(userDTO.getEmail(), existUser.getPassword(), authorities); // create a new UserDetails with the new email
            Authentication newAuth = new UsernamePasswordAuthenticationToken(newUserDetails, null, authorities); // create a new authentication token
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
        ModelMapper modelMapper = new ModelMapper();
        UserDTO newUser = modelMapper.map(userDTO, UserDTO.class);
        newUser.setId(existUser.getId());
        newUser.setPassword(existUser.getPassword());
        newUser.setMatchingPassword(existUser.getMatchingPassword());
        if(newUser.getFile() != null && !newUser.getFile().isEmpty()) {
            fileService.uploadFile(newUser.getFile());
            newUser.setPhoto(newUser.getFile().getOriginalFilename());
        }else{
            newUser.setPhoto(existUser.getPhoto());
        }
        newUser.setId(existUser.getId());
        newUser.setPassword(existUser.getPassword());
        String role = "";
        for (Role r: existUser.getRoles()) {
                role=r.getName();

        }
        newUser.setRoleName(role);
        int idUser =  userService.update(newUser);
        redirectAttrs.addFlashAttribute("message", "your profile is updated successfully");
        redirectAttrs.addFlashAttribute("alertType", "alert-success");

        return "redirect:/profile";

    }

}
