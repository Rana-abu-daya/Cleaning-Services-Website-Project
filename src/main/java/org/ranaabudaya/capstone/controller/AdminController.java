package org.ranaabudaya.capstone.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ranaabudaya.capstone.dto.ServicesDTO;
import org.ranaabudaya.capstone.entity.Admin;
import org.ranaabudaya.capstone.entity.Services;
import org.ranaabudaya.capstone.repository.AdminRepository;
import org.ranaabudaya.capstone.repository.UserRepository;
import org.ranaabudaya.capstone.service.AdminService;
import org.ranaabudaya.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class AdminController {

    AdminRepository adminRepository;
    UserService userService;
    AdminService adminService;


    @Autowired
    public AdminController(AdminRepository adminRepository, UserService userService, AdminService adminService){
        this.adminRepository =adminRepository;
        this.userService=userService;
        this.adminService = adminService;
    }

    @GetMapping("/admins")
    private String AllAdmin(Model model)
    {
        List<Admin> adminList = adminService.getAll();
        model.addAttribute("adminList", adminList);
        return "admins";
    }

    @GetMapping("/admins/delete/{id}")
    @ResponseBody
    public String[] deleteAdminbyId(@PathVariable("id") int id, Model model) {
        int result =  adminService.deleteById(id);
        String arr[] = new String[2];
        if(result>0){
            arr[0] = "The admin is deleted successfully";
            arr[1]= "success";
            return  arr;

        }else {
            arr[0] = "The admin's deletion failed, there is one admin";
            arr[1]= "danger";
            return  arr;

        }

    }
    @GetMapping("/admins/edit-admin/{id}")
    public String editAdminbyId(@PathVariable("id") int id, Model model) {
        Optional<Admin> admin = adminService.findAdminById(id);
        model.addAttribute("admin",admin);
        model.addAttribute("id", id);
        return "edit-admin";
    }
    @PostMapping("/admins/updateAdmin/{id}")
    public String updateServices(@PathVariable("id") int id, @Valid @ModelAttribute ("admin") Admin admin, BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) {
        System.out.println(admin.toString());
        System.out.println(admin.getUser().toString());
        if(bindingResult.hasErrors())

        {

            model.addAttribute("id", id);
            // log.warn("Wrong attempt");
            return "edit-admin";
        }
        if(userService.findUserByEmail(admin.getUser().getEmail()) != null) {
            model.addAttribute("duplicateEmail", "Email is used in Homey");
            return "edit-admin";
        }
        Optional<Admin> admin1 = adminService.findAdminById(id);
        if (admin1.isPresent()) {
            admin1.get().setUser(admin.getUser());
            adminService.update(admin1.get());
            redirectAttrs.addFlashAttribute("message", "Admin is updated successfully");
            redirectAttrs.addFlashAttribute("alertType", "alert-success");
        }else{

            redirectAttrs.addFlashAttribute("message", " Admin cannot be found");
            redirectAttrs.addFlashAttribute("alertType", "alert-danger");
        }

        return "redirect:/admins";

    }



}
