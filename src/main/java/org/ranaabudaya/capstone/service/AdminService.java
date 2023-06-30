package org.ranaabudaya.capstone.service;

import org.ranaabudaya.capstone.dto.AdminDTO;
import org.ranaabudaya.capstone.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AdminService {


    public int deleteById(int id);
    public void create(AdminDTO adminDTO);

    public Optional<Admin> findAdminById(int id);
    public List<Admin> getAll();
    public Page<Admin> getAllPagination(Pageable pageable);
    public void update(Admin admin);

}
