package org.ranaabudaya.capstone.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "message")
public class Message {

    @NotNull
    private String fullname;
    @NotNull
    private String email;
    @NotNull
    private String message;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
}
