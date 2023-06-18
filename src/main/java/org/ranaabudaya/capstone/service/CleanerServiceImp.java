package org.ranaabudaya.capstone.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.ranaabudaya.capstone.dto.CleanerDTO;
import org.ranaabudaya.capstone.entity.Cleaner;
import org.ranaabudaya.capstone.entity.User;
import org.ranaabudaya.capstone.repository.CleanerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Optional;

public class CleanerServiceImp implements CleanerService{
   CleanerRepository cleanerRepository;
   @Autowired
   public CleanerServiceImp(CleanerRepository cleanerRepository){
       this.cleanerRepository = cleanerRepository;
   }
    @Override
    public void create(CleanerDTO cleanerDTO) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Cleaner cleaner = modelMapper.map(cleanerDTO, Cleaner.class);
        cleanerRepository.save(cleaner);
    }

    @Override
    public Optional<Cleaner> findCleanerById(int id) {

        return  cleanerRepository.findById(id);
    }
}
