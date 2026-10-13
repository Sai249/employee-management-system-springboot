package com.employee.management.dto;

import com.employee.management.model.Employee;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;

@Schema(description = "Employee returned by the API")
public class EmployeeResponse {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String department;
    private final String jobTitle;
    private final BigDecimal salary;
    private final Instant createdAt;
    private final Instant updatedAt;

    private EmployeeResponse(Employee employee) {
        id = employee.getId();
        firstName = employee.getFirstName();
        lastName = employee.getLastName();
        email = employee.getEmail();
        department = employee.getDepartment();
        jobTitle = employee.getJobTitle();
        salary = employee.getSalary();
        createdAt = employee.getCreatedAt();
        updatedAt = employee.getUpdatedAt();
    }

    public static EmployeeResponse from(Employee employee) { return new EmployeeResponse(employee); }
    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getDepartment() { return department; }
    public String getJobTitle() { return jobTitle; }
    public BigDecimal getSalary() { return salary; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
