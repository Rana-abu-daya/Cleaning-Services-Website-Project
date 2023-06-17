package org.ranaabudaya.capstone.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class ServicesDTO {
    @NotBlank(message = "Please enter the Service name")
    private String name;
    @Min(value = 1, message = "Price must be at least {value}")
    private double price;
    @NotBlank(message = "Please enter the Service description")
    private String description;
    @NotNull
    private boolean active;
}
