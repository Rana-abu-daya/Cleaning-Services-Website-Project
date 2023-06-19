package org.ranaabudaya.capstone.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ranaabudaya.capstone.entity.Services;
import org.ranaabudaya.capstone.entity.User;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class CleanerDTO {
    @NotNull
    @Min(value = 1, message = "number of hours must be at least {value}")
    private int hours;
    @NotNull
    private LocalTime startTime;
   // @NotNull
    private String resume;

    @Valid
    @NotEmpty(message = "Choose at least one service")
    private Collection<Services> services;
    //@NotNull
    private boolean isActive;
    @NotNull
    private String about_me;
    @NotNull
    private int userId;
    @Override
    public String toString() {
        System.out.println("#123# printing Cleaner DTO:");
        String s =  "CleanerDTO{" +
                "hours=" + hours +
                ", startTime=" + startTime +
                ", resume='" + resume + '\'' +
                ", services=" + services.toArray() +
                ", isActive=" + isActive +
                ", about_me='" + about_me + '\'' +
                ", userId=" + userId +
                '}';

        return s;
    }

}
