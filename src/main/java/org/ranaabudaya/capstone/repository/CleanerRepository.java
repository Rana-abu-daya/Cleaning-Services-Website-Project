package org.ranaabudaya.capstone.repository;

import org.ranaabudaya.capstone.entity.Cleaner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CleanerRepository extends JpaRepository<Cleaner,Integer> {

    @Query("SELECT c FROM Cleaner c JOIN c.services s WHERE s.id = :serviceId")
     List<Cleaner> findAllByServicesId(Integer serviceId);

}
