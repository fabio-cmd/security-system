package com.fiap.security_system.controller;

import com.fiap.security_system.exception.BadRequestException;
import com.fiap.security_system.exception.NotFoundException;
import com.fiap.security_system.model.*;
import com.fiap.security_system.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {
    private final IncidentService incidentService;

    @Autowired
    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
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
    public ResponseEntity<Incident> createIncident(@RequestBody Incident incident) {
        if (incident.getTitle() == null || incident.getTitle().isEmpty()) {
            throw new BadRequestException("Por favor, insira os dados corretamente para cadastrar um incidente");
        }
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

    @GetMapping("/responsible")
    public ResponseEntity<List<Incident>> getIncidentsByResponsible(@RequestBody Employee employee) {
        List<Incident> incidents = incidentService.getIncidentsByResponsible(employee);
        return ResponseEntity.ok(incidents);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Incident> updateIncidentStatus(@PathVariable Long id, @RequestBody STATUS newStatus) {
        Incident incident = incidentService.getIncidentById(id)
                .orElseThrow(() -> new NotFoundException("Incidente não encontrado com o ID: " + id));

        incident.setStatus(newStatus);

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
