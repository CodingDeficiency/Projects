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

    public Integer addEmployee(String firstName, String lastName, String email) {
        Employee employee = new Employee(firstName, lastName, email);
        Employee savedEmployee = employeeRepository.save(employee);
        return savedEmployee.getId();
    }

    public Employee getEmployee(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.orElse(null);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

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

    public boolean deleteEmployee(Integer id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Employee searchByName(String firstName, String lastName) {
        Optional<Employee> employee = employeeRepository.findByFirstNameAndLastName(firstName, lastName);
        return employee.orElse(null);
    }

    public boolean validateEmployeeData(String firstName, String lastName, String email) {
        if (firstName == null || firstName.trim().isEmpty()) {
            return false;
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            return false;
        }
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        if (!email.contains("@") || !email.contains(".")) {
            return false;
        }
        return true;
    }

    public boolean employeeExists(Integer id) {
        return employeeRepository.existsById(id);
    }
}