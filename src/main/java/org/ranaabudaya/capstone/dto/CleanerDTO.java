package org.ranaabudaya.capstone.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ranaabudaya.capstone.entity.Services;
import org.ranaabudaya.capstone.entity.User;

import java.time.LocalTime;
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
    @NotNull
    private String resume;
    @NotNull
    private Collection<Services> services;
    @NotNull
    private boolean isActive;
    @NotNull
    private String about_me;
    @NotNull
    private int userId;
}
