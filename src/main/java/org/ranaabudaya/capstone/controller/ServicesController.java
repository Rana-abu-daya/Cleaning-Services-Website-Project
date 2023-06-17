package org.ranaabudaya.capstone.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.ranaabudaya.capstone.dto.ServicesDTO;
import org.ranaabudaya.capstone.entity.Services;
import org.ranaabudaya.capstone.service.ServicesServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/services")
public class ServicesController {

    ServicesServiceImp servicesServiceImp;
    @Autowired
    public ServicesController(ServicesServiceImp servicesServiceImp) {
        this.servicesServiceImp = servicesServiceImp;
    }

    @ModelAttribute("ServicesList")
    private List<Services> getServices(){
        List<Services> list =  servicesServiceImp.getAllServices();
        return list;
    }
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/services")
    public String servicesListView(){
        return "services";
    }

    @GetMapping("/add-service")
    public String addService(@ModelAttribute("service") ServicesDTO serviceDTO){

        return "newService";
    }

    @PostMapping("/saveService")
    public String saveService(@Valid @ModelAttribute ("service") ServicesDTO serviceDTO, BindingResult bindingResult, RedirectAttributes redirectAttrs){
        System.out.println(serviceDTO);

        if(bindingResult.hasErrors())
        {
           // log.warn("Wrong attempt");
            return "newService";
        }

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        serviceDTO.setDescription(serviceDTO.getDescription().replace("\n", "**"));
        Services service = modelMapper.map(serviceDTO, Services.class);
        servicesServiceImp.saveService(service);
        redirectAttrs.addFlashAttribute("message", "The service is added successfully");
        redirectAttrs.addFlashAttribute("alertType", "alert-success");


        return "redirect:/services/services";

    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String deleteServicebyId(@PathVariable("id") int id) {
       int result =  servicesServiceImp.deleteById(id);
       if(result>0){
           return  "success**The service is deleted successfully";
       }else {
           return  "danger**The service is deleted failed";

       }
    }
    @GetMapping("/edit-service/{id}")
    public String editServicebyId(@PathVariable("id") int id, Model model) {
        Optional<Services> s = servicesServiceImp.getServiceById(id);
        model.addAttribute("service", s);
        model.addAttribute("id", id);
        return "edit-service";
    }

    @PostMapping("/updateService/{id}")
    public String updateServices(@PathVariable("id") int id, @Valid @ModelAttribute ("service") ServicesDTO serviceDTO, BindingResult bindingResult,Model model,RedirectAttributes redirectAttrs) {

        if(bindingResult.hasErrors())

        {

            model.addAttribute("id", id);
            // log.warn("Wrong attempt");
            return "edit-service";
        }

        Optional<Services> serv = servicesServiceImp.getServiceById(id);
        serviceDTO.setDescription(serviceDTO.getDescription().replace("\n", "**"));
        if (serv.isPresent()) {
            Services updatedService = serv.get();
            updatedService.setName(serviceDTO.getName());
            updatedService.setDescription(serviceDTO.getDescription());
            updatedService.setActive(serviceDTO.isActive());
            updatedService.setPrice(serviceDTO.getPrice());
            servicesServiceImp.saveService(updatedService);
            redirectAttrs.addFlashAttribute("message", "The service is updated successfully");
            redirectAttrs.addFlashAttribute("alertType", "alert-success");
        }else{

            redirectAttrs.addFlashAttribute("message", "The service cannot be found");
            redirectAttrs.addFlashAttribute("alertType", "alert-danger");
        }

        return "redirect:/services/services";

    }

}
