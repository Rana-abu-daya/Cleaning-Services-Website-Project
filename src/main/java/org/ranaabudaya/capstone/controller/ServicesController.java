package org.ranaabudaya.capstone.controller;

import org.ranaabudaya.capstone.entity.Services;
import org.ranaabudaya.capstone.service.ServicesServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

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
    @GetMapping("/services")
    public String servicesListView(){
        return "services1";
    }

    @GetMapping("/add-service")
    public String addService(@ModelAttribute("service") Services service){

        return "newService";
    }
}
