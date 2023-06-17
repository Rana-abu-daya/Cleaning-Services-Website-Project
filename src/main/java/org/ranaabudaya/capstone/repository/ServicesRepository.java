package org.ranaabudaya.capstone.repository;

import org.ranaabudaya.capstone.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ServicesRepository extends JpaRepository<Services, Integer> {

    public Services findServicesByName(String name);
    public List<Services> findByActiveTrue();
}
