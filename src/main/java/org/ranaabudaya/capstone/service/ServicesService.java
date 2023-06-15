package org.ranaabudaya.capstone.service;

import org.ranaabudaya.capstone.entity.Services;

import java.util.List;

public interface ServicesService {

    public void saveService(Services service);
    public Services findServiceByName(String name);
    public List<Services> getAllServices();

}
