package org.ranaabudaya.capstone.repository;

import org.ranaabudaya.capstone.entity.Cleaner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CleanerRepository extends JpaRepository<Cleaner,Integer> {

    @Query("SELECT c FROM Cleaner c JOIN c.services s WHERE s.id = :serviceId")
     List<Cleaner> findAllByServicesId(Integer serviceId);
    List<Cleaner> findCleanerByIsNew(boolean isNew);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cleaner_services WHERE cleaner_id = :cleanerId", nativeQuery = true)
    void deleteCleanerServicesByCleanerId(int cleanerId);

}
