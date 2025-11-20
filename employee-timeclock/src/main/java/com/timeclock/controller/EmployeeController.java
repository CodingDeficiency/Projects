package com.timeclock.controller;

import com.timeclock.model.Employee;
import com.timeclock.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping()
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Integer id) {
        Employee employee = employeeService.getEmployee(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addEmployee(@RequestBody Map<String, String> employeeData) {
        String firstName = employeeData.get("firstName");
        String lastName = employeeData.get("lastName");
        String email = employeeData.get("email");

        Map<String, Object> response = new HashMap<>();

        if (employeeService.validateEmployeeData(firstName, lastName, email)) {
            Integer id = employeeService.addEmployee(firstName, lastName, email);

            response.put("success", true);
            response.put("employeeId", id);
            response.put("message", "Employee added successfully!");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        response.put("success", false);
        response.put("message", "Invalid employee data. Please check all fields.");
        return ResponseEntity.badRequest().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateEmployee(
            @PathVariable Integer id,
            @RequestBody Map<String, String> employeeData) {

        String firstName = employeeData.get("firstName");
        String lastName = employeeData.get("lastName");
        String email = employeeData.get("email");

        boolean success = employeeService.updateEmployee(id, firstName, lastName, email);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "Employee updated successfully!" : "Employee not found");

        return success ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteEmployee(@PathVariable Integer id) {
        boolean success = employeeService.deleteEmployee(id);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "Employee deleted successfully!" : "Employee not found");

        return success ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }
}