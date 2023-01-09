package com.alex.hornsby.employees.services;

import com.alex.hornsby.amqp.RabbitMQMessageProducer;
import com.alex.hornsby.employees.EmployeesConfig;
import com.alex.hornsby.employees.entity.Employee;
import com.alex.hornsby.employees.repository.EmployeesRepository;
import com.alex.hornsby.employees.dto.EmployeeRegistrationRequest;
import com.alex.hornsby.employees.dto.EmployeeUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * The service for the employees service.
 */
@Service
@AllArgsConstructor
public class EmployeesService {

    private final EmployeesRepository employeesRepository;
private final EmployeesConfig employeesConfig;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    /**
     *
     EmployeeRegistrationMessage message = new EmployeeRegistrationMessage(employee.getFirstName(), employee.getLastName(), employee.getEmail(), new Date());
     * Registers a new employee.
     *
     * @param request the employee registration request object {@link EmployeeRegistrationRequest}
     */
    public Employee registerEmployee(EmployeeRegistrationRequest request) {
        Employee employee = Employee.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        employeesRepository.save(employee);

        rabbitMQMessageProducer.publish(employee, employeesConfig.getInternalExchange(), employeesConfig.getInternalEmployeesRoutingKey());

        return employee;
    }

    public Optional<Employee> getEmployee(UUID employeeId) {
        return employeesRepository.findById(employeeId);
    }

    public Employee[] getEmployees() {
        return employeesRepository.findAll().toArray(new Employee[0]);
    }

    public Employee updateEmployee(UUID employeeId, EmployeeUpdateRequest employeeUpdateRequest) {
        Employee employee = employeesRepository.findById(employeeId).orElse(null);

        if (employee == null) {
            //TODO: add custom exception
            throw new IllegalArgumentException("Employee with ID " + employeeId + " does not exist");
        }

        employee.setFirstName(employeeUpdateRequest.firstName());
        employee.setLastName(employeeUpdateRequest.lastName());
        employee.setEmail(employeeUpdateRequest.email());

        employeesRepository.save(employee);
        return employee;
    }

    public void deleteEmployee(UUID employeeId) {
        if (employeesRepository.existsById(employeeId)) {
            employeesRepository.deleteById(employeeId);
        }
    }
}
