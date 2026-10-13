package com.employee.management.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Schema(description = "Data required to create or update an employee")
public class EmployeeRequest {

    @NotBlank @Size(max = 60)
    private String firstName;
    @NotBlank @Size(max = 60)
    private String lastName;
    @NotBlank @Email @Size(max = 120)
    private String email;
    @NotBlank @Size(max = 80)
    private String department;
    @NotBlank @Size(max = 100)
    private String jobTitle;
    @NotNull @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal salary;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }
}
