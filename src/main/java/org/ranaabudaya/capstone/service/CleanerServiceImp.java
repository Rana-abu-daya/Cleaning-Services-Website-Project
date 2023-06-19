package org.ranaabudaya.capstone.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.ranaabudaya.capstone.dto.CleanerDTO;
import org.ranaabudaya.capstone.entity.Cleaner;
import org.ranaabudaya.capstone.entity.User;
import org.ranaabudaya.capstone.repository.CleanerRepository;
import org.ranaabudaya.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
@Service
@Slf4j
public class CleanerServiceImp implements CleanerService{
   CleanerRepository cleanerRepository;
   UserRepository userRepository;
   @Autowired
   public CleanerServiceImp(CleanerRepository cleanerRepository,UserRepository userRepository){
       this.cleanerRepository = cleanerRepository;
       this.userRepository = userRepository;
   }
    @Override
    public void create(CleanerDTO cleanerDTO) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Cleaner cleaner = modelMapper.map(cleanerDTO, Cleaner.class);
        User user = userRepository.findById(cleanerDTO.getUserId()).orElse(null);
        cleaner.setUser(user);
        cleanerRepository.save(cleaner);
    }

    @Override
    public Optional<Cleaner> findCleanerById(int id) {

        return  cleanerRepository.findById(id);
    }
}
