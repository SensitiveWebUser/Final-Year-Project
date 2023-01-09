package com.alex.hornsby.employees.controller;

import com.alex.hornsby.employees.dto.EmployeeRegistrationMessage;
import com.alex.hornsby.employees.dto.EmployeeRegistrationRequest;
import com.alex.hornsby.employees.dto.EmployeeUpdateRequest;
import com.alex.hornsby.employees.entity.Employee;
import com.alex.hornsby.employees.services.EmployeesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * The controller for the employees service.
 */
@Slf4j
@RestController
@RequestMapping("api/v1/employees")
@AllArgsConstructor
public class EmployeesController {

    private final EmployeesService employeesService;

    /**
     * Registers a new employee.
     *
     * @param employeeRegistrationRequest the employee registration request object {@link EmployeeRegistrationRequest}
     */
    @PutMapping
    public ResponseEntity<Employee> registerEmployee(@RequestBody EmployeeRegistrationRequest employeeRegistrationRequest) {
        log.info("Received request for employee {}", employeeRegistrationRequest);
        Employee employee = employeesService.registerEmployee(employeeRegistrationRequest);

        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    /**
     * Registers new employees
     *
     * @param employeeRegistrationRequests an array of {@link EmployeeRegistrationRequest} objects
     */
    @PutMapping("/bulk")
    public ResponseEntity<Employee[]> registerEmployees(@RequestBody EmployeeRegistrationRequest[] employeeRegistrationRequests) {
        log.info("Received request for employees {}", employeeRegistrationRequests);
        return new ResponseEntity<>(Arrays.stream(employeeRegistrationRequests)
                .map(employeesService::registerEmployee)
                .toArray(Employee[]::new), HttpStatus.CREATED);
    }

    /**
     * Gets an employee by their ID.
     *
     * @param employeeId the employee ID
     * @return the employee
     */
    @GetMapping("/{employees_id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("employees_id") UUID employeeId) {
        log.info("Received request for employee {}", employeeId);
        Optional<Employee> employee = employeesService.getEmployee(employeeId);
        return employee.get() != null
                ? new ResponseEntity<>(employee.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Get all employees.
     *
     * @return all employees
     */
    @GetMapping("/bulk")
    public ResponseEntity<Employee[]> getEmployees() {
        log.info("Received request for all employees");
        return new ResponseEntity<>(employeesService.getEmployees(), HttpStatus.OK);
    }

    /**
     * Updates an employee.
     *
     * @param employeeId the employee ID
     * @param employeeUpdateRequest the employee update request
     */
    @PatchMapping("/{employees_id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("employees_id") UUID employeeId, @RequestBody EmployeeUpdateRequest employeeUpdateRequest) {
        log.info("Received request to update employee {} with {}", employeeId, employeeUpdateRequest);
        return new ResponseEntity<>(employeesService.updateEmployee(employeeId, employeeUpdateRequest), HttpStatus.OK);
    }

    /**
     * Updates employees information.
     * @param employeeUpdateRequests an array of {@link EmployeeUpdateRequest} objects
     */
    @PatchMapping("/bulk")
    public ResponseEntity<Employee[]> updateEmployees(@RequestBody EmployeeUpdateRequest[] employeeUpdateRequests) {
        log.info("Received request to update employees {}", employeeUpdateRequests);
        return new ResponseEntity<>(Arrays.stream(employeeUpdateRequests)
                .map(employeeUpdateRequest -> employeesService.updateEmployee(employeeUpdateRequest.employeeId(), employeeUpdateRequest))
                .toArray(Employee[]::new), HttpStatus.OK);
    }

    /**
     * Deletes an employee.
     *
     * @param employeeId the employee ID
     */
    @DeleteMapping("/{employees_id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("employees_id") UUID employeeId) {
        log.info("Received request to delete employee {}", employeeId);
        employeesService.deleteEmployee(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes all employees.
     *
     * @param employeeIds the employee IDs to delete
     */
    @DeleteMapping("/bulk")
    public ResponseEntity<Void> deleteEmployees(@RequestBody UUID[] employeeIds) {
        log.info("Received request to delete employees {}", employeeIds);
        for (UUID employeeId : employeeIds) {
            employeesService.deleteEmployee(employeeId);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
