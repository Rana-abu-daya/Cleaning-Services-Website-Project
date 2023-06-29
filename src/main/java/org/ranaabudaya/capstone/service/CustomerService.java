package org.ranaabudaya.capstone.service;

import org.ranaabudaya.capstone.dto.CustomerDTO;
import org.ranaabudaya.capstone.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    public int deleteById(int id);
    public void create(CustomerDTO customerDTO);

    public Optional<Customer> findCustomerById(int id);
    public List<Customer> getAll();
    public void update(Customer customer);
    public Customer findCustomerByUserId(int id);
}
