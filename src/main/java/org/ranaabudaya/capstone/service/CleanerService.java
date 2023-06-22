package org.ranaabudaya.capstone.service;

import org.ranaabudaya.capstone.dto.CleanerDTO;
import org.ranaabudaya.capstone.dto.UserDTO;
import org.ranaabudaya.capstone.entity.Admin;
import org.ranaabudaya.capstone.entity.Cleaner;
import org.ranaabudaya.capstone.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface CleanerService {



    public void create(CleanerDTO cleanerDTO);

    public Optional<Cleaner> findCleanerById(int id);
    public List<Cleaner> getAll();
    public  int deleteById(int id);



}
