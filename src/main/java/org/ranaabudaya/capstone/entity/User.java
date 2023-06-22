package org.ranaabudaya.capstone.entity;


import lombok.*;

import jakarta.persistence.*;
import java.util.Collection;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String userName;

    private String firstName;

    private String lastName;
    private String photo;
    private String email;

    private String phone;
    private String state;
    private String city;
    private String zipCode;
    private String address;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection <Role> roles;

    public User(int id, String userName, String firstName, String lastName, String photo, String email, String phone, String state, String zipCode, String address, String password, Collection<Role> roles) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
        this.email = email;
        this.phone = phone;
        this.state = state;
        this.zipCode = zipCode;
        this.address = address;
        this.password = password;
        this.roles = roles;
    }
}