package org.ranaabudaya.capstone.service;

import org.ranaabudaya.capstone.dto.CustomerDTO;
import org.ranaabudaya.capstone.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    public int deleteById(int id);
    public void create(CustomerDTO customerDTO);

    public Optional<Customer> findCustomerById(int id);
    public List<Customer> getAll();
    public Page<Customer> getAllActivePagination(Pageable pageable);

    public Page<Customer> getAllDeleted(Pageable pageable);

    public void update(Customer customer);
    public void activateById(int  id);
    public Customer findCustomerByUserId(int id);
}
