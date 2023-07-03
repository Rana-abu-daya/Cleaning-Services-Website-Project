package org.ranaabudaya.capstone.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//the customer is the person who has the bookings
@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(name = "is_deleted")
    private  boolean isDeleted =false;
}
