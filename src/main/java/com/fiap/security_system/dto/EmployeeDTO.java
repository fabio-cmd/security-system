package com.fiap.security_system.dto;

import com.fiap.security_system.model.ROLES;

public record EmployeeDTO(
        ROLES role,
        String documentId
) {}
