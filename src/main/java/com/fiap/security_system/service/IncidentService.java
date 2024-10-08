package com.fiap.security_system.service;

import com.fiap.security_system.model.*;
import com.fiap.security_system.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidentService {
    private final IncidentRepository incidentRepository;

    @Autowired
    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public Incident saveIncident(Incident incident) {
        return incidentRepository.save(incident);
    }

    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    public Optional<Incident> getIncidentById(Long id) {
        return incidentRepository.findById(id);
    }

    public void deleteIncidentById(Long id) {
        incidentRepository.deleteById(id);
    }

    public List<Incident> getIncidentsByStatus(STATUS status) {
        return incidentRepository.findByStatus(status);
    }

    public List<Incident> getIncidentsByResponsible(Employee employee) {
        return incidentRepository.findByResponsible(employee);
    }
}
