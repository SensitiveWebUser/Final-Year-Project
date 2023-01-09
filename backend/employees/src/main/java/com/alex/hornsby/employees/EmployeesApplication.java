package com.alex.hornsby.employees;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main application class for the employees service.
 */
@SpringBootApplication(
        scanBasePackages = {
                "com.alex.hornsby.employees",
                "com.alex.hornsby.amqp"
        }
)
public class EmployeesApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeesApplication.class, args);
    }
}
