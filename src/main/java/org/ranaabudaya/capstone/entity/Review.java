package org.ranaabudaya.capstone.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String comment;
    private int ratingValue;
    @Valid
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    private Booking booking;


}
