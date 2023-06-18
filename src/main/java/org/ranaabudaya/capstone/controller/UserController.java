package org.ranaabudaya.capstone.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.ranaabudaya.capstone.dto.CleanerDTO;
import org.ranaabudaya.capstone.dto.UserDTO;
import org.ranaabudaya.capstone.entity.Services;
import org.ranaabudaya.capstone.entity.User;
import org.ranaabudaya.capstone.service.CleanerService;
import org.ranaabudaya.capstone.service.ServicesService;
import org.ranaabudaya.capstone.service.ServicesServiceImp;
import org.ranaabudaya.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import  org.ranaabudaya.capstone.helper.FormWrapper;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
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
    @Autowired
    public UserController(UserService userDetailsService, ServicesService servicesServiceImp , CleanerService cleanerService) {
        this.userService = userDetailsService;
        this.servicesServiceImp = servicesServiceImp;
        this.cleanerService = cleanerService;
    }

    @GetMapping("/")
    private String redirectToLogin()
    {
        return "redirect:/login";
    }


    @GetMapping("/cleaner/sign-up")
    public String signUp(Model model)
    {
        model.addAttribute("formWrapper", new FormWrapper());
        List<Services> list =  servicesServiceImp.getAllServices();
        model.addAttribute("servicesList",list );
//        model.addAttribute("userDto", new UserDTO());
//        model.addAttribute("cleanerDTO", new CleanerDTO());
        return "sign-up-cleaner";
    }

    @PostMapping("/cleaner/signup-process")
    public String signupProcess(@Valid @ModelAttribute ("formWrapper") FormWrapper formWrapper, BindingResult bindingResult
    , @RequestParam(value = "services" , required = false) int[] services , BindingResult resultservices, Model model, RedirectAttributes redirectAttrs)
    {
        if(bindingResult.hasErrors() || resultservices.hasErrors() )
        {
            List<Services> list =  servicesServiceImp.getAllServices();
            model.addAttribute("servicesList",list );
           // log.warn("Wrong attempt");
            return "sign-up-cleaner";
        }
        System.out.println(formWrapper.getUserDTO());
        System.out.println(formWrapper.getCleanerDTO());
        System.out.println(Arrays.toString(services));
        CleanerDTO cleanerDTO = formWrapper.getCleanerDTO();
        formWrapper.getUserDTO().setRoleName("ROLE_CLEANER");
        int userId = userService.create(formWrapper.getUserDTO());
        cleanerDTO.setUserId(userId);
        cleanerDTO.setActive(false);
        cleanerService.create(cleanerDTO);
        redirectAttrs.addFlashAttribute("message", "Welcome to Homey.. ");
        redirectAttrs.addFlashAttribute("alertType", "alert-success");

        //userService.create(userDTO);
        return "redirect:/dashboard";
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
