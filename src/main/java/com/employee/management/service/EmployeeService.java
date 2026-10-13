package com.employee.management.service;

import com.employee.management.dto.EmployeeRequest;
import com.employee.management.dto.EmployeeResponse;
import com.employee.management.dto.PageResponse;
import com.employee.management.exception.DuplicateResourceException;
import com.employee.management.exception.ResourceNotFoundException;
import com.employee.management.model.Employee;
import com.employee.management.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class EmployeeService {
    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    private static final Set<String> SORTABLE_FIELDS = new HashSet<>(Arrays.asList(
            "id", "firstName", "lastName", "email", "department", "jobTitle", "salary", "createdAt", "updatedAt"));

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public PageResponse<EmployeeResponse> findAll(int page, int size, String sortBy, String direction) {
        if (!SORTABLE_FIELDS.contains(sortBy)) {
            throw new IllegalArgumentException("sortBy must be one of: " + String.join(", ", SORTABLE_FIELDS));
        }
        Sort.Direction sortDirection;
        try {
            sortDirection = Sort.Direction.fromString(direction);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("direction must be asc or desc");
        }
        Page<Employee> employees = employeeRepository.findAll(
                PageRequest.of(page, size, Sort.by(sortDirection, sortBy)));
        return PageResponse.from(employees, EmployeeResponse::from);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public EmployeeResponse findById(Long id) {
        return EmployeeResponse.from(getEmployee(id));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public EmployeeResponse create(EmployeeRequest request) {
        String email = normalizeEmail(request.getEmail());
        if (employeeRepository.existsByEmailIgnoreCase(email)) {
            throw new DuplicateResourceException("An employee with this email already exists");
        }
        Employee employee = new Employee(request.getFirstName().trim(), request.getLastName().trim(), email,
                request.getDepartment().trim(), request.getJobTitle().trim(), request.getSalary());
        Employee saved = employeeRepository.save(employee);
        log.info("Employee created: id={}, email={}", saved.getId(), saved.getEmail());
        return EmployeeResponse.from(saved);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee employee = getEmployee(id);
        String email = normalizeEmail(request.getEmail());
        if (employeeRepository.existsByEmailIgnoreCaseAndIdNot(email, id)) {
            throw new DuplicateResourceException("An employee with this email already exists");
        }
        employee.update(request.getFirstName().trim(), request.getLastName().trim(), email,
                request.getDepartment().trim(), request.getJobTitle().trim(), request.getSalary());
        Employee saved = employeeRepository.save(employee);
        log.info("Employee updated: id={}", saved.getId());
        return EmployeeResponse.from(saved);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        Employee employee = getEmployee(id);
        employeeRepository.delete(employee);
        log.info("Employee deleted: id={}, email={}", id, employee.getEmail());
    }

    private Employee getEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " was not found"));
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
