package com.alex.hornsby.employees.dto;

import com.alex.hornsby.employees.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeRegistrationMessage implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private Date registrationDate;

}
