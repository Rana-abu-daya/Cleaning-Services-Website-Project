package org.ranaabudaya.capstone.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    public Role(String name) {
        this.name = name;
    }
}