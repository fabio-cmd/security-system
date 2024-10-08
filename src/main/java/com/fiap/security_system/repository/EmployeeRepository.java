package com.fiap.security_system.repository;


import com.fiap.security_system.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByRole(ROLES role);
}