package org.ranaabudaya.capstone.dto;

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
    @NotBlank
    private String name;
    @NotNull
    private double price;
    @NotBlank
    private String description;
    @NotNull
    private boolean active;
}
