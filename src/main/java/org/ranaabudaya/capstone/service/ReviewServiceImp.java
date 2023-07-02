package org.ranaabudaya.capstone.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.ranaabudaya.capstone.dto.BookingDTO;
import org.ranaabudaya.capstone.dto.ReviewDTO;
import org.ranaabudaya.capstone.entity.Admin;
import org.ranaabudaya.capstone.entity.Booking;
import org.ranaabudaya.capstone.entity.Review;
import org.ranaabudaya.capstone.entity.User;
import org.ranaabudaya.capstone.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ReviewServiceImp implements ReviewService {
    ReviewRepository reviewRepository;
    BookingService bookingService;
    @Autowired
    public ReviewServiceImp(ReviewRepository reviewRepository,BookingService bookingService){
        this.reviewRepository=reviewRepository;
        this.bookingService =bookingService;
    }
    @Override
    public int deleteById(int id) {
        return 0;
    }

    @Override
    public void create(ReviewDTO reviewDTO) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Review  review = modelMapper.map(reviewDTO, Review.class);
        if(this.findBookingById(reviewDTO.getBookingId()) != null){
            Review existingReview = this.findBookingById(reviewDTO.getBookingId());
            existingReview.setComment(reviewDTO.getComment());
            existingReview.setRatingValue(reviewDTO.getRatingValue());
            existingReview.setBooking(existingReview.getBooking());
            reviewRepository.save(review);
        }else {
            Booking booking = bookingService.findBookingById(reviewDTO.getBookingId()).orElse(null);
            review.setBooking(booking);
            reviewRepository.save(review);
        }


    }

    @Override
    public Review findBookingById(int bookingId) {
        return reviewRepository.findByBookingId(bookingId);
    }

    @Override
    public int deleteByBookingId(int bookingId) {
        if(this.findBookingById(bookingId)!= null){
            reviewRepository.deleteByBookingId(bookingId);
            return 1;
        }
        return 0;
    }
}
