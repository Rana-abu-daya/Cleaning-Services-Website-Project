package org.ranaabudaya.capstone.repository;

import org.ranaabudaya.capstone.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicesRepository extends JpaRepository<Services, Integer> {

    public Services findServicesByName(String name);
    public List<Services> findByActiveTrue();
}
