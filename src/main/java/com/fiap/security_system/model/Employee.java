package com.fiap.security_system.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String documentId;

    @OneToMany(mappedBy = "responsible", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Incident> incidents;

    @Enumerated(EnumType.STRING)
    private ROLES role;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public List<Incident> getIncidents() {
        return incidents;
    }

    public void setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
    }
}
