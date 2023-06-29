package org.ranaabudaya.capstone.repository;

import org.ranaabudaya.capstone.entity.Admin;
import org.ranaabudaya.capstone.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Customer findCustomerByUserId(int id);
}
