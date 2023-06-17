package org.ranaabudaya.capstone.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ranaabudaya.capstone.dto.CleanerDTO;
import org.ranaabudaya.capstone.dto.UserDTO;
import org.ranaabudaya.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


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

    @Autowired
    public UserController(UserService userDetailsService) {
        this.userService = userDetailsService;
    }

    @GetMapping("/")
    private String redirectToLogin()
    {
        return "redirect:/login";
    }


    @GetMapping("/cleaner/sign-up")
    public String signUp(Model model)
    {
        model.addAttribute("userDto", new UserDTO());
        model.addAttribute("cleanerDTO", new CleanerDTO());
        return "sign-up-cleaner";
    }

    @PostMapping("/signup-process")
    public String signupProcess(@Valid @ModelAttribute ("userDto") UserDTO userDTO, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            log.warn("Wrong attempt");
            return "sign-up";
        }
        userService.create(userDTO);
        return "confirmation";
    }

    /**
     * In order to
     * make code more readable
     * it is good practice to
     * use special DTOs for login
     * It also make controllers
     * less dependent from entities
     * and separate validation from
     * jpa functionality
     * @return
     */
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
