package com.fiap.security_system.repository;


import com.fiap.security_system.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole(ROLES role);
}