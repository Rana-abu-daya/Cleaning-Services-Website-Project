package org.ranaabudaya.capstone.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.ranaabudaya.capstone.dto.AdminDTO;
import org.ranaabudaya.capstone.dto.CustomerDTO;
import org.ranaabudaya.capstone.entity.Admin;
import org.ranaabudaya.capstone.entity.Customer;
import org.ranaabudaya.capstone.entity.User;
import org.ranaabudaya.capstone.repository.AdminRepository;
import org.ranaabudaya.capstone.repository.CustomerRepository;
import org.ranaabudaya.capstone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerServiceImp implements CustomerService{

    CustomerRepository customerRepository;
    UserRepository userRepository;
    @Autowired
    public CustomerServiceImp(CustomerRepository customerRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }


    public  int deleteById(int id){
        if(customerRepository.findById(id).isPresent()) {
            customerRepository.deleteById(id);
            return 1;
        }
        else{
            return 0;
        }



    }
    public Customer findCustomerByUserId(int id){
        return customerRepository.findCustomerByUserId(id);

    }
    @Override
    public void create(CustomerDTO customerDTO) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        User user = userRepository.findById(customerDTO.getUserId()).orElse(null);
        customer.setUser(user);
        customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> findCustomerById(int id) {

        return  customerRepository.findById(id);
    }
    @Override
    public List<Customer> getAll(){
        return customerRepository.findAll();
    }
    public void update(Customer customer){

        customerRepository.save(customer);
    }
}
