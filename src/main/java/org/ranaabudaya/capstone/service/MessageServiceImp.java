package org.ranaabudaya.capstone.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.ranaabudaya.capstone.dto.MessageDTO;
import org.ranaabudaya.capstone.entity.Admin;
import org.ranaabudaya.capstone.entity.Message;
import org.ranaabudaya.capstone.entity.User;
import org.ranaabudaya.capstone.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImp implements MessageService{
    @Autowired
    MessageRepository messageRepository;
    public void create(MessageDTO messageDTO){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Message message = modelMapper.map(messageDTO, Message.class);

        messageRepository.save(message);
    }
}
