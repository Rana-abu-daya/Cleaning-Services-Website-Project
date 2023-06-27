package org.ranaabudaya.capstone.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.ranaabudaya.capstone.dto.AdminDTO;
import org.ranaabudaya.capstone.dto.CleanerDTO;
import org.ranaabudaya.capstone.dto.UserDTO;
import org.ranaabudaya.capstone.entity.Services;
import org.ranaabudaya.capstone.entity.User;
import org.ranaabudaya.capstone.helper.AdminformWrapper;
import org.ranaabudaya.capstone.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import  org.ranaabudaya.capstone.helper.FormWrapper;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


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
    @Autowired
    public UserController(UserService userDetailsService, ServicesService servicesServiceImp , CleanerService cleanerService,
                          AdminService adminService) {
        this.userService = userDetailsService;
        this.servicesServiceImp = servicesServiceImp;
        this.cleanerService = cleanerService;
        this.adminService=  adminService;
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
    ,  Model model, RedirectAttributes redirectAttrs)
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
            int userId = userService.create(formWrapper.getUserDTO());
           // System.out.println(userId + "Rana");
            cleanerDTO.setUserId(userId);
            cleanerDTO.setActive(false);
            cleanerDTO.setNew(true);
            cleanerService.create(cleanerDTO);
            redirectAttrs.addFlashAttribute("message", "Welcome to Homey.. ");
            redirectAttrs.addFlashAttribute("alertType", "alert-success");

            //userService.create(userDTO);
            return "redirect:/dashboard";
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

    @GetMapping("/login")
    public String getLoginPage()
    {
        log.info("Login page displayed");
        return "login";
    }


    @RequestMapping("/home")
    public String getHome()
    {
        log.info("home page displayed");
        return "home";
    }

}
