package org.ranaabudaya.capstone.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.*;
@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "services")
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank
    private String name;
    @NotNull
    private double price;
    @NotBlank
    private String description;
    @NotNull
    private boolean active;

}
