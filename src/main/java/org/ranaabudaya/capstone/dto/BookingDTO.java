package org.ranaabudaya.capstone.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Future;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BookingDTO {

    @NotNull
    private int serviceId;

    @Override
    public String toString() {
        return "BookingDTO{" +
                "serviceId=" + serviceId +
                ", date='" + date + '\'' +
                ", hours=" + hours +
                ", cleanerId=" + cleanerId +
                ", fullName='" + fullName + '\'' +
                ", customerId=" + customerId +
                ", zipCode='" + zipCode + '\'' +
                ", address='" + address + '\'' +
                ", StartTime='" + StartTime + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @NotNull
    private String date;
    @NotNull
    @Min(value = 1, message = "Price must be at least {value}")
    private int hours;
    @NotNull
    private int cleanerId;

    @NotNull
    private String fullName;
    @NotNull
    private int customerId;
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






}
