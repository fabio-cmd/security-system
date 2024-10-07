package com.fiap.security_system.repository;

import com.fiap.security_system.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByStatus(STATUS status);
    List<Incident> findByResponsible(User user);
}