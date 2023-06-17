package org.ranaabudaya.capstone.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.Collection;

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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "cleaner_services",
            joinColumns = @JoinColumn(name = "cleaner_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Collection<Services> services;
    private boolean isActive;
    private String about_me;
    private int hours;
    private LocalTime startTime;
    private String resume;

}
