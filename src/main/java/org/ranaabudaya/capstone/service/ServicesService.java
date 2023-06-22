package org.ranaabudaya.capstone.service;

import org.ranaabudaya.capstone.entity.Services;

import java.util.List;
import java.util.Optional;

public interface ServicesService {

    public void saveService(Services service);
    public Services findServiceByName(String name);
    public List<Services> getAllServices();
    public List<Services> getAllActiveServices();

    public List<Services> getActiveServies();
    public int deleteById(int id);
    public Optional<Services> getServiceById(int id);

}
