package org.ranaabudaya.capstone.repository;

import org.ranaabudaya.capstone.entity.Cleaner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface CleanerRepository extends JpaRepository<Cleaner,Integer> {

    @Query("SELECT c FROM Cleaner c JOIN c.services s WHERE s.id = :serviceId")
     List<Cleaner> findAllByServicesId(Integer serviceId);
    @Query("SELECT c FROM Cleaner c JOIN c.services s WHERE s.id = :serviceId AND c.isActive = true AND isNew = false")
    List<Cleaner> findAllActiveByServicesId(Integer serviceId);

//    @Query(value = """
//        SELECT c.*
//        FROM cleaner c
//        JOIN cleaner_services cs ON c.id = cs.cleaner_id
//        WHERE cs.service_id = :serviceId AND c.is_active = 1 AND c.is_new = 0
//        AND c.id NOT IN (
//            SELECT b.cleaner_id
//            FROM booking b
//            WHERE b.date = :bookingDate
//            AND (
//                (STR_TO_DATE(b.start_time, '%H:%i') <= STR_TO_DATE(:startTime, '%H:%i') AND ADDTIME(STR_TO_DATE(b.start_time, '%H:%i'), SEC_TO_TIME(b.hours * 3600)) > STR_TO_DATE(:startTime, '%H:%i'))
//                OR (STR_TO_DATE(b.start_time, '%H:%i') < ADDTIME(STR_TO_DATE(:startTime, '%H:%i'), SEC_TO_TIME(:hours * 3600)) AND ADDTIME(STR_TO_DATE(b.start_time, '%H:%i'), SEC_TO_TIME(b.hours * 3600)) >= ADDTIME(STR_TO_DATE(:startTime, '%H:%i'), SEC_TO_TIME(:hours * 3600)))
//                OR (STR_TO_DATE(b.start_time, '%H:%i') >= STR_TO_DATE(:startTime, '%H:%i') AND ADDTIME(STR_TO_DATE(b.start_time, '%H:%i'), SEC_TO_TIME(b.hours * 3600)) <= ADDTIME(STR_TO_DATE(:startTime, '%H:%i'), SEC_TO_TIME(:hours * 3600)))
//            )
//        )
//        AND STR_TO_DATE(c.start_time, '%H:%i') <= STR_TO_DATE(:startTime, '%H:%i')
//        AND ADDTIME(STR_TO_DATE(c.start_time, '%H:%i'), SEC_TO_TIME(c.hours * 3600)) >= ADDTIME(STR_TO_DATE(:startTime, '%H:%i'), SEC_TO_TIME(:hours * 3600)) limit 5
//        """, nativeQuery = true)
//


@Query(value = """
        SELECT c.*
        FROM cleaner c
        JOIN cleaner_services cs ON c.id = cs.cleaner_id
        WHERE cs.service_id = :serviceId AND c.is_active = 1 AND c.is_new = 0
        AND c.id NOT IN (
            SELECT b.cleaner_id
            FROM booking b
            WHERE DATE(b.date) = :bookingDate
            AND (
                (STR_TO_DATE( b.start_time, '%H:%i') < STR_TO_DATE(:startTime, '%H:%i') AND ADDTIME(STR_TO_DATE( b.start_time, '%H:%i'), SEC_TO_TIME(b.hours * 3600)) > ADDTIME(STR_TO_DATE(:startTime, '%H:%i'), SEC_TO_TIME(:hours * 3600)))
                OR (STR_TO_DATE( b.start_time, '%H:%i') < STR_TO_DATE(:startTime, '%H:%i') AND ADDTIME(STR_TO_DATE( b.start_time, '%H:%i'), SEC_TO_TIME(b.hours * 3600)) > STR_TO_DATE(:startTime, '%H:%i') AND ADDTIME(STR_TO_DATE( b.start_time, '%H:%i'), SEC_TO_TIME(b.hours * 3600)) <= ADDTIME(STR_TO_DATE(:startTime, '%H:%i'), SEC_TO_TIME(:hours * 3600)))
                OR (STR_TO_DATE( b.start_time, '%H:%i') >= STR_TO_DATE(:startTime, '%H:%i') AND STR_TO_DATE( b.start_time, '%H:%i') < ADDTIME(STR_TO_DATE(:startTime, '%H:%i'), SEC_TO_TIME(:hours * 3600)) AND ADDTIME(STR_TO_DATE( b.start_time, '%H:%i'), SEC_TO_TIME(b.hours * 3600)) > ADDTIME(STR_TO_DATE(:startTime, '%H:%i'), SEC_TO_TIME(:hours * 3600)))
                OR (STR_TO_DATE( b.start_time, '%H:%i') >= STR_TO_DATE(:startTime, '%H:%i') AND ADDTIME(STR_TO_DATE( b.start_time, '%H:%i'), SEC_TO_TIME(b.hours * 3600)) <= ADDTIME(STR_TO_DATE(:startTime, '%H:%i'), SEC_TO_TIME(:hours * 3600)))
            )
        )
        AND STR_TO_DATE( c.start_time, '%H:%i') <= STR_TO_DATE(:startTime, '%H:%i')
        AND ADDTIME(STR_TO_DATE( c.start_time, '%H:%i'), SEC_TO_TIME(c.hours * 3600)) >= ADDTIME(STR_TO_DATE(:startTime, '%H:%i'), SEC_TO_TIME(:hours * 3600))
        """, nativeQuery = true)
    List<Cleaner> findAvailableCleanersForServiceAndTime(
            String startTime, int hours, LocalDate bookingDate, int serviceId);

    List<Cleaner> findCleanerByIsNew(boolean isNew);
    Page<Cleaner> findByIsNew(boolean isNew, Pageable pageable);
    Page<Cleaner> findByIsActive(boolean isActive, Pageable pageable);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cleaner_services WHERE cleaner_id = :cleanerId", nativeQuery = true)
    void deleteCleanerServicesByCleanerId(int cleanerId);

    Cleaner findCleanerByUserId(int id);
    List<Cleaner> findByIsActiveAndIsNew(boolean isActive, boolean isNew);


}
