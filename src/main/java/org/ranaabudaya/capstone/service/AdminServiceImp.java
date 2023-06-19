package org.ranaabudaya.capstone.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.ranaabudaya.capstone.dto.AdminDTO;
import org.ranaabudaya.capstone.dto.CleanerDTO;
import org.ranaabudaya.capstone.entity.Admin;
import org.ranaabudaya.capstone.entity.Cleaner;
import org.ranaabudaya.capstone.entity.User;
import org.ranaabudaya.capstone.repository.AdminRepository;
import org.ranaabudaya.capstone.repository.CleanerRepository;
import org.ranaabudaya.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AdminServiceImp implements AdminService{
   AdminRepository adminRepository;
   UserRepository userRepository;
    @Autowired
    public AdminServiceImp(AdminRepository adminRepository, UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
    }



    @Override
    public void create(AdminDTO adminDTO) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Admin admin = modelMapper.map(adminDTO, Admin.class);
        User user = userRepository.findById(adminDTO.getUserId()).orElse(null);
        admin.setUser(user);
        adminRepository.save(admin);
    }

    @Override
    public Optional<Admin> findCleanerById(int id) {

        return  adminRepository.findById(id);
    }
}
