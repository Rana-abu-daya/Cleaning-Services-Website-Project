package org.ranaabudaya.capstone.service;

import org.ranaabudaya.capstone.dto.AdminDTO;
import org.ranaabudaya.capstone.dto.ReviewDTO;
import org.ranaabudaya.capstone.entity.Admin;
import org.ranaabudaya.capstone.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    public int deleteById(int id);
    public void create(ReviewDTO reviewDTO);

    public Review findBookingById(int bookingId);
    public int deleteByBookingId(int bookingId);
}
