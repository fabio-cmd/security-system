package com.fiap.security_system.controller;

import com.fiap.security_system.exception.*;
import com.fiap.security_system.model.Employee;
import com.fiap.security_system.model.ROLES;
import com.fiap.security_system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createUser(@RequestBody Employee employee) {
        if (employee.getDocumentId() == null) {
            throw new BadRequestException("Informe a matricula do funcionario.");
        }

        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllUsers() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new NotFoundException("Funcionario não encontrado com o ID: " + id));
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<Employee>> getEmployeesByRole(@PathVariable ROLES role) {
        List<Employee> employees = employeeService.getEmployeeByRole(role);
        return ResponseEntity.ok(employees);
    }
}
