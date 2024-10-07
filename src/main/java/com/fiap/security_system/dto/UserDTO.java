package com.fiap.security_system.dto;

import com.fiap.security_system.model.ROLES;

public record UserDTO(
        String username,
        String password,
        ROLES role,
        String documentId
) {}
