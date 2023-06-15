package org.ranaabudaya.capstone.controller;


import org.ranaabudaya.capstone.entity.Services;
import org.ranaabudaya.capstone.service.ServicesServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class mainController {

    ServicesServiceImp servicesServiceImp;
    @Autowired
    public mainController(ServicesServiceImp servicesServiceImp) {
        this.servicesServiceImp = servicesServiceImp;
    }

    @ModelAttribute("ServicesList")
    private List<List<Services>> getServices(){
        List<Services> list =  servicesServiceImp.getAllServices();
        List<List<Services>> finals = new ArrayList<>();
        int sublistSize = 3;

        for (int i = 0; i < list.size(); i += sublistSize) {
            int endIndex = Math.min(i + sublistSize, list.size());
            List<Services> sublist = list.subList(i, endIndex);
            finals.add(sublist);
        }
        return finals;
    }
    @GetMapping(value = {"/","/home", "/homey"})
    public String home(){


        return "index";
    }
}
