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

import java.util.List;
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


    public  int deleteById(int id){
        List<Admin> list = adminRepository.findAll();
        if(list.size()>1){
           Optional<Admin> admin= adminRepository.findById(id);
           adminRepository.deleteById(id);
           User adminUser = admin.get().getUser();
            System.out.println(adminUser);
           return 1;
        }else{
            return 0;

        }


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
    public Optional<Admin> findAdminById(int id) {

        return  adminRepository.findById(id);
    }
    @Override
    public List<Admin> getAll(){
        return adminRepository.findAll();
    }
    public void update(Admin admin){
        adminRepository.save(admin);
    }
}
