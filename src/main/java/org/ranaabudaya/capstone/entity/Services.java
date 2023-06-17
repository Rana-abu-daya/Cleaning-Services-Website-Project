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
    private String name;
    private double price;
    private String description;
    private boolean active;

    public Services(int id, String name, double price, String description, boolean active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.active = active;

    }

}
