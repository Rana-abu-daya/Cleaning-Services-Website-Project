package org.ranaabudaya.capstone.service;

import org.ranaabudaya.capstone.entity.Services;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface ServicesService {

    public void saveService(Services service);
    public Services findServiceByName(String name);
    public Page<Services> getAllServices(Pageable page);
    public List<Services> getAllServicesWithoutPage();
    public List<Services> getAllActiveServices();

    public List<Services> getActiveServies();
    public int deleteById(int id);
    public Services getServiceByName(String name);
    public Optional<Services> getServiceById(int id);

}
