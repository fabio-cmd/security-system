package com.fiap.security_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "incidents")
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User responsible;

    private String title;

    @Enumerated(EnumType.STRING)
    private STATUS status;

    @Enumerated(EnumType.STRING)
    private CRIME_TYPE type;

    private String description;

    private String localization;
}
