package org.ranaabudaya.capstone.service;

import org.ranaabudaya.capstone.entity.Services;
import org.ranaabudaya.capstone.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesServiceImp implements ServicesService {

    private ServicesRepository servicesRepository;
    @Autowired
    public ServicesServiceImp(ServicesRepository serviceRepository) {
        this.servicesRepository = serviceRepository;
    }

    @Override
    public void saveService(Services service) {
        servicesRepository.save(service);
        return;
    }

    @Override
    public Services findServiceByName(String name) {
       Services s = servicesRepository.findServicesByName(name);
        return s;
    }

    @Override
    public List<Services> getAllServices() {
        List<Services> list= servicesRepository.findAll();
        return list;
    }
    @Override
    public List<Services> getActiveServies(){
        List<Services> list= servicesRepository.findByActiveTrue();
        return list;

    }
}
