package org.ranaabudaya.capstone.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDTO {

    private String comment;
    private int ratingValue;
    private int bookingId;

}
