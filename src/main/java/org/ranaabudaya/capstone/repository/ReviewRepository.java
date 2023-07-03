package org.ranaabudaya.capstone.repository;

import org.ranaabudaya.capstone.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
    public Review findByBookingId(int bookingId);
    public  void deleteByBookingId(int bookingId);
    public List<Review> findTop3ByOrderByRatingValueDesc();
}
