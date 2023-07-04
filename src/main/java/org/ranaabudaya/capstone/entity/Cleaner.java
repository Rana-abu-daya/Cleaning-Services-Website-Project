package org.ranaabudaya.capstone.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.Collection;
//cleaner is the employee
//first it sign in then the admin approved them so he can be take for bookings
@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "cleaner")
public class Cleaner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @NotEmpty(message = "Choose at least one service")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "cleaner_services",
            joinColumns = @JoinColumn(name = "cleaner_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Collection<Services> services;
    private boolean isActive;
    private boolean isNew;
    @NotNull
    private String about_me;
    @NotNull
    @Min(value = 1, message = "number of hours must be at least {value}")
    private int hours;
    @NotNull
    private String startTime;


    private String resume;

    @Transient
    private Double averageRating;

}
