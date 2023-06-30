package org.ranaabudaya.capstone.service;

import org.ranaabudaya.capstone.dto.CleanerDTO;
import org.ranaabudaya.capstone.dto.UserDTO;
import org.ranaabudaya.capstone.entity.Admin;
import org.ranaabudaya.capstone.entity.Cleaner;
import org.ranaabudaya.capstone.entity.Services;
import org.ranaabudaya.capstone.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CleanerService {



    public void create(CleanerDTO cleanerDTO);

    public Optional<Cleaner> findCleanerById(int id);
    public List<Cleaner> getAll();
    public Cleaner findByUserId(int id);
    public  int deleteById(int id);
public List<Cleaner> findAllCleaner();
public List<Cleaner> getCleanersByServiceId(int id);
public List<Cleaner>  findAvailableCleanersForServiceAndTime(String startTime,int hours, LocalDate bookingDate, int serviceId);
public List<Integer> checkUpdatedServices(int id,  Collection<Services> services);
    public boolean updateCleanerSchedule(int cleanerId, String newStartTime, int newHours);

}
