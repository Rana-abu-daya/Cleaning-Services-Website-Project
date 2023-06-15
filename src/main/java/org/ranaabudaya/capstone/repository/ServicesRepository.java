package org.ranaabudaya.capstone.repository;

import org.ranaabudaya.capstone.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicesRepository extends JpaRepository<Services, Integer> {

    public Services findServicesByName(String name);
}
