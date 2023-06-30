package org.ranaabudaya.capstone.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Valid
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private Services service;
    @NotNull
    @Future(message = "Please select a date in the future")
    private Date date;
    @NotNull
    @Min(value = 1, message = "Price must be at least {value}")
    private int hours;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cleaner_id")
    private Cleaner cleaner;

    @NotNull
    private String fullName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @NotEmpty(message = "Required")
    @Pattern(regexp = "[0-9]{5}$", message = "Zip code wrong format")
    private String zipCode;
    @NotNull
    private String address;
    @NotNull
    private String StartTime;
    @NotEmpty(message = "Required")
    @Pattern(regexp = "^\\+?[0-9\\s]+$", message = "Invalid phone number format. Please provide a valid phone number.")
    private String phone;
    private BookingStatus status;


    public enum BookingStatus {
        NEW,
        IN_PROGRESS,
        CANCELLED,
        SUCCESS
    }
}
