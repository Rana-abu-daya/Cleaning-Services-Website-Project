package org.ranaabudaya.capstone.service;

import org.ranaabudaya.capstone.dto.AdminDTO;
import org.ranaabudaya.capstone.entity.Admin;

import java.util.Optional;

public interface AdminService {



    public void create(AdminDTO adminDTO);

    public Optional<Admin> findCleanerById(int id);


}
