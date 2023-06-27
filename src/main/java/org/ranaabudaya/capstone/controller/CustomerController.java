package org.ranaabudaya.capstone.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.ranaabudaya.capstone.dto.UserDTO;
import org.ranaabudaya.capstone.entity.Admin;
import org.ranaabudaya.capstone.entity.Customer;
import org.ranaabudaya.capstone.repository.CustomerRepository;
import org.ranaabudaya.capstone.service.CustomerService;
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
public class CustomerController {

    CustomerRepository customerRepository;
    CustomerService customerService;
    UserService userService;

    @Autowired
    public CustomerController(CustomerRepository customerRepository,CustomerService customerService, UserService userService){
        this.customerRepository = customerRepository;
        this.customerService = customerService;
        this.userService=userService;
    }

    @GetMapping("/customers")
    private String AllAdmin(Model model)
    {
        List<Customer> customerList = customerService.getAll();
        model.addAttribute("customerList", customerList);
        return "customers";
    }

    @GetMapping("/customers/delete/{id}")
    @ResponseBody
    public String[] deleteAdminbyId(@PathVariable("id") int id, Model model) {
        int result =  customerService.deleteById(id);
        String arr[] = new String[2];
        if(result>0){
            arr[0] = "The customer is deleted successfully";
            arr[1]= "success";
            return  arr;

        }else {
            arr[0] = "The customer's deletion failed.";
            arr[1]= "danger";
            return  arr;

        }

    }
    @GetMapping("/customers/edit-customer/{id}")
    public String editAdminbyId(@PathVariable("id") int id, Model model) {
        Optional<Customer> customer = customerService.findCustomerById(id);
        model.addAttribute("customer",customer);
        model.addAttribute("id", id);
        return "edit-customer";
    }
    @PostMapping("/customers/updateCustomer/{id}")
    public String updateServices(@PathVariable("id") int id, @Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult, Model model, RedirectAttributes redirectAttrs) {
        System.out.println(customer.toString());
        System.out.println(customer.getUser().toString());
        if(bindingResult.hasErrors())

        {

            model.addAttribute("id", id);
            return "edit-customer";
        }

        Optional<Customer> customer1 = customerService.findCustomerById(id);
        if (customer1.isPresent()) {

            if(userService.findUserByEmail(customer.getUser().getEmail()) != null && userService.findUserByEmail(customer.getUser().getEmail()).getId() != customer1.get().getUser().getId())
            {
                model.addAttribute("duplicateEmail","Email is used in Homey" );
                return "edit-customer";
            }
            ModelMapper modelMapper = new ModelMapper();

            UserDTO userDTO = modelMapper.map(customer.getUser(), UserDTO.class);
            userDTO.setRoleName("ROLE_CLIENT");
            userDTO.setId(customer1.get().getUser().getId());
            userDTO.setPassword(customer1.get().getUser().getPassword());
            int idUser =  userService.update(userDTO);
            customer.setUser(userService.findById(idUser).get());

            customerRepository.save(customer);
            redirectAttrs.addFlashAttribute("message", "customer is updated successfully");
            redirectAttrs.addFlashAttribute("alertType", "alert-success");
        }else{

            redirectAttrs.addFlashAttribute("message", " customer cannot be found");
            redirectAttrs.addFlashAttribute("alertType", "alert-danger");
        }

        return "redirect:/customers";

    }



}
