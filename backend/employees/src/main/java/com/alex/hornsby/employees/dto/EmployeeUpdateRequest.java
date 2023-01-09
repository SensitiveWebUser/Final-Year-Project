package com.alex.hornsby.employees.dto;

import java.util.UUID;

public record EmployeeUpdateRequest(UUID employeeId, String firstName, String lastName, String email) {
}
