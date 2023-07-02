package org.ranaabudaya.capstone.service;

import lombok.extern.slf4j.Slf4j;
import org.ranaabudaya.capstone.entity.Services;
import org.ranaabudaya.capstone.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ServicesServiceImp implements ServicesService {

    private ServicesRepository servicesRepository;
    @Autowired
    public ServicesServiceImp(ServicesRepository serviceRepository) {
        this.servicesRepository = serviceRepository;
    }
    public Services getServiceByName(String name){
        return servicesRepository.findServicesByName(name);
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
    public Page<Services> getAllServices(Pageable page) {
        Page<Services>  list= servicesRepository.findAll(page);
        return list;
    }
    @Override
    public List<Services> getAllServicesWithoutPage() {
        List<Services>  list= servicesRepository.findAll();
        return list;
    }

    @Override
    public List<Services> getActiveServies(){
        List<Services> list= servicesRepository.findByActiveTrue();
        return list;

    }
    @Override
    public List<Services> getAllActiveServices(){
        return getActiveServies();
    }

    @Override
    public int deleteById(int id) {
        boolean exist = servicesRepository.existsById(id);
        if(exist){
            servicesRepository.deleteById(id);
            return 1;
        }else {
            return 0;
        }
    }
    @Override
    public Optional<Services> getServiceById(int id){
        Optional<Services> s = servicesRepository.findById(id);
        return s;
    }
}
