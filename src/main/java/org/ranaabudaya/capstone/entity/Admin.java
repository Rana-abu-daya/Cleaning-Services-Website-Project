package org.ranaabudaya.capstone.entity;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.Collection;
//Admin entity is for future use,, for any aditional information we could need
//like
//Role/Permission Levels,ManagedBy, AssignedSections, LastActivity. AuditLogs

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Valid
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;



}
