package com.alex.hornsby.employees.repository;

import com.alex.hornsby.employees.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeesRepository extends JpaRepository<Employee, UUID> {
}
