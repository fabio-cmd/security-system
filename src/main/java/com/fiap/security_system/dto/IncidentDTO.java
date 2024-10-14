package com.fiap.security_system.dto;

import com.fiap.security_system.model.*;

public record IncidentDTO(
        String title,
        CRIME_TYPE type,
        String description,
        String localization,
        STATUS status,
        long responsibleId
) {}
