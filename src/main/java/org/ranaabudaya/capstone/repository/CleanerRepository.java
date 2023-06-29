package org.ranaabudaya.capstone.repository;

import org.ranaabudaya.capstone.entity.Cleaner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
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

//    @Query(value = "SELECT c.* " +
//            "FROM cleaner c " +
//            "JOIN cleaner_service cs ON cs.cleaner_id = c.id " +
//            "JOIN service s ON s.id = cs.service_id " +
//            "LEFT JOIN booking b ON b.cleaner_id = c.id " +
//            "WHERE s.id = :serviceId " +
//            "AND c.is_active = 1 " +
//            "AND c.is_new = 0 " +
//            "AND STR_TO_DATE(c.start_time, '%H:%i:%s') <= STR_TO_DATE(:startTime, '%H:%i') " +
//            "AND ADDTIME(STR_TO_DATE(c.start_time, '%H:%i:%s'), INTERVAL c.hours HOUR) >= STR_TO_DATE(:endTime, '%H:%i') " +
//            "AND NOT EXISTS (" +
//            "   SELECT 1 " +
//            "   FROM booking b2 " +
//            "   WHERE b2.cleaner_id = c.id " +
//            "   AND b2.date = :bookingDate " +
//            "   AND ADDTIME(STR_TO_DATE(b2.start_time, '%H:%i:%s'), INTERVAL b2.hours HOUR) >= STR_TO_DATE(:endTime, '%H:%i') " +
//            "   AND STR_TO_DATE(b2.start_time, '%H:%i:%s') <= STR_TO_DATE(:startTime, '%H:%i')" +
//            ")", nativeQuery = true)

    @Query(value = "SELECT c.* " +
            "FROM Cleaner AS c " +
            "JOIN Cleaner_Services AS cs ON cs.cleaner_id = c.id " +
            "LEFT JOIN Booking AS b ON b.cleaner_id = c.id " +
            "AND b.date = :bookingDate " +
            "AND STR_TO_DATE(b.start_time, '%H:%i') BETWEEN STR_TO_DATE(:startTime, '%H:%i') AND " +
            "DATE_ADD(STR_TO_DATE(:startTime, '%H:%i'), INTERVAL :hours HOUR) " +
            "WHERE cs.service_id = :serviceId " +
            "AND c.is_active = 1 " +
            "AND c.is_new = 0 " +
            "AND b.id IS NULL " +
            "AND STR_TO_DATE(c.start_time, '%H:%i') <= STR_TO_DATE(:startTime, '%H:%i') " +
            "AND DATE_ADD(STR_TO_DATE(c.start_time, '%H:%i'), INTERVAL c.hours HOUR) >= DATE_ADD(STR_TO_DATE(:startTime, '%H:%i'), INTERVAL :hours HOUR) "+"LIMIT 5", nativeQuery = true)
    List<Cleaner> findAvailableCleanersForServiceAndTime(
            String startTime, int hours, LocalDate bookingDate, int serviceId);

    List<Cleaner> findCleanerByIsNew(boolean isNew);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cleaner_services WHERE cleaner_id = :cleanerId", nativeQuery = true)
    void deleteCleanerServicesByCleanerId(int cleanerId);

}
