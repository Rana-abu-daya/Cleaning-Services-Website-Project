package org.ranaabudaya.capstone.repository;

import org.ranaabudaya.capstone.entity.Cleaner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CleanerRepository extends JpaRepository<Cleaner,Integer> {

}
