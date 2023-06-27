package org.ranaabudaya.capstone.entity;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
    @NotEmpty
    private String userName;
    @NotEmpty(message = "Required")
    @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed")
    private String firstName;
    @NotEmpty(message = "Required")
    @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed")
    private String lastName;
    private String photo;
    @Email(message = "Invalid email format. Please provide a valid email address.",regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Required")
    private String email;
    @NotEmpty(message = "Required")
    @Pattern(regexp = "^\\+?[0-9\\s]+$", message = "Invalid phone number format. Please provide a valid phone number.")
    private String phone;
    @NotEmpty(message = "Required")
    private String state;
    @NotEmpty(message = "Required")
    private String city;
    @NotEmpty(message = "Required")
    @Pattern(regexp = "[0-9]{5}$", message = "Zip code wrong format")
    private String zipCode;
    @NotEmpty(message = "Required")
    private String address;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection <Role> roles;

    public User(int id, String userName, String firstName, String lastName, String photo, String email, String phone, String state, String zipCode, String address, String password, Collection<Role> roles, String matchingPassword) {
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