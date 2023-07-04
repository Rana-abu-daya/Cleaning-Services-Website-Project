package org.ranaabudaya.capstone.controller;


import org.ranaabudaya.capstone.entity.Services;
import org.ranaabudaya.capstone.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
//This controller is an example of REST web sesrvices using Spring  Data REST
//used Service entity
@RestController
@RequestMapping("/api")
public class WebServiceController {

        @Autowired
        private ServicesService servicesService;

        @GetMapping("/services")
        public List<Services> getAllCleaners(@RequestParam(required = false) String title) {
                return servicesService.getAllActiveServices();

        }

        @GetMapping("/services/{id}")
        public Optional<Services> getsServiceById(@PathVariable("id") int id) {
                return servicesService.getServiceById(id);
        }

        @PostMapping("/services")
        public void createservices(@RequestBody Services services) {
                servicesService.saveService(services);
        }

        @PutMapping("/services/{id}")
        public void updateservices(@PathVariable("id") int id, @RequestBody Services services) {
                Optional<Services> serviceData = servicesService.getServiceById(id);

                if (serviceData.isPresent()) {
                        Services oldService = serviceData.get();
                        oldService.setName(services.getName());
                        oldService.setDescription(services.getDescription());
                        oldService.setActive(services.isActive());
                        oldService.setPrice(services.getPrice());

                        servicesService.saveService(oldService);
                }
        }

        @DeleteMapping("/services/{id}")
        public void deleteservices(@PathVariable("id") int id) {
                servicesService.deleteById(id);
        }
}
