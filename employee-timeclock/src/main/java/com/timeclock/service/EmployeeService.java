package com.timeclock.service;

import com.timeclock.model.Employee;
import com.timeclock.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Add a new employee to the system
     */
    public Integer addEmployee(String firstName, String lastName, String email) {
        Employee employee = new Employee(firstName, lastName, email);
        Employee savedEmployee = employeeRepository.save(employee);
        return savedEmployee.getId();
    }

    /**
     * Get employee by ID
     */
    public Employee getEmployee(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.orElse(null);
    }

    /**
     * Get all employees
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Update employee details
     */
    public boolean updateEmployee(Integer id, String firstName, String lastName, String email) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setEmail(email);
            employeeRepository.save(employee);
            return true;
        }
        return false;
    }

    /**
     * Delete employee (also deletes all punch records due to CASCADE)
     */
    public boolean deleteEmployee(Integer id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Search employee by name
     */
    public Employee searchByName(String firstName, String lastName) {
        Optional<Employee> employee = employeeRepository.findByFirstNameAndLastName(firstName, lastName);
        return employee.orElse(null);
    }

    /**
     * Validate employee data before saving
     */
    public boolean validateEmployeeData(String firstName, String lastName, String email) {
        // Check first name
        if (firstName == null || firstName.trim().isEmpty()) {
            return false;
        }
        // Check last name
        if (lastName == null || lastName.trim().isEmpty()) {
            return false;
        }
        // Check email
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        // Basic email validation
        if (!email.contains("@") || !email.contains(".")) {
            return false;
        }
        return true;
    }

    /**
     * Check if employee exists
     */
    public boolean employeeExists(Integer id) {
        return employeeRepository.existsById(id);
    }
}