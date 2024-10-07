package com.fiap.security_system.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private ROLES role;

    private String documentId;

    @OneToMany(mappedBy = "responsible", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Incident> incidents;
}
