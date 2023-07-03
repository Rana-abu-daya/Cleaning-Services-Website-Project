package org.ranaabudaya.capstone.repository;

import org.ranaabudaya.capstone.entity.Admin;
import org.ranaabudaya.capstone.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findCustomerByUserId(int id);
    List<Customer> findByIsDeletedTrue();
    Page<Customer> findByIsDeletedTrue(Pageable pageable);
    Page<Customer> findByIsDeletedFalse(Pageable pageable);
    List<Customer> findByIsDeletedFalse();

}
