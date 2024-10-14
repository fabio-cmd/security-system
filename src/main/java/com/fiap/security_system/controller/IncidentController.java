package com.fiap.security_system.controller;

import com.fiap.security_system.dto.IncidentDTO;
import com.fiap.security_system.dto.StatusUpdateDTO;
import com.fiap.security_system.exception.BadRequestException;
import com.fiap.security_system.exception.NotFoundException;
import com.fiap.security_system.model.*;
import com.fiap.security_system.service.EmployeeService;
import com.fiap.security_system.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {
    private final IncidentService incidentService;
    private final EmployeeService employeeService;

    @Autowired
    public IncidentController(IncidentService incidentService, EmployeeService employeeService) {
        this.incidentService = incidentService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Incident>> getAllIncidents() {
        List<Incident> incidents = incidentService.getAllIncidents();
        if (incidents.isEmpty()) {
            throw new NotFoundException("Ainda não há incidentes");
        }
        return ResponseEntity.ok(incidents);
    }

    @PostMapping
    public ResponseEntity<Incident> createIncident(@RequestBody @Validated IncidentDTO incidentDTO) {
        Employee responsible = employeeService.getEmployeeById(incidentDTO.responsibleId())
                .orElseThrow(() -> new NotFoundException("Employee não encontrado com id: " + incidentDTO.responsibleId()));


        Incident incident = new Incident();
        incident.setTitle(incidentDTO.title());
        incident.setType(incidentDTO.type());
        incident.setDescription(incidentDTO.description());
        incident.setLocalization(incidentDTO.localization());
        incident.setStatus(incidentDTO.status());
        incident.setResponsible(responsible);

        Incident savedIncident = incidentService.saveIncident(incident);

        return ResponseEntity.ok(savedIncident);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable long id) {
        Incident incident = incidentService.getIncidentById(id).orElseThrow(() -> new NotFoundException("Incidente não encontrado !"));
        return ResponseEntity.ok(incident);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Incident>> getIncidentsByStatus(@PathVariable STATUS status) {
        List<Incident> incidents = incidentService.getIncidentsByStatus(status);
        return ResponseEntity.ok(incidents);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Incident> updateIncidentStatus(@PathVariable Long id, @RequestBody StatusUpdateDTO statusUpdateDTO) {
        Incident incident = incidentService.getIncidentById(id)
                .orElseThrow(() -> new NotFoundException("Incidente não encontrado com o ID: " + id));

        try {
            STATUS status = STATUS.valueOf(statusUpdateDTO.getNewStatus().toUpperCase());
            incident.setStatus(status);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Status inválido: " + statusUpdateDTO.getNewStatus());
        }

        Incident updatedIncident = incidentService.saveIncident(incident);
        return ResponseEntity.ok(updatedIncident);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncidentById(@PathVariable Long id) {
        incidentService.getIncidentById(id)
                .orElseThrow(() -> new NotFoundException("Incidente não encontrado com o ID: " + id));

        incidentService.deleteIncidentById(id);
        return ResponseEntity.noContent().build();
    }

}
