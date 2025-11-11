package com.timeclock.controller;

import com.timeclock.model.Employee;
import com.timeclock.service.EmployeeService;
import com.timeclock.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/payment/{id}")
    public ResponseEntity<Map<String, Object>> calculatePayment(@PathVariable Integer id) {
        Employee employee = employeeService.getEmployee(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        double payment = reportService.calculatePayment(id);
        Map<String, Double> dailyHours = reportService.calculateDailyHours(id);

        Map<String, Object> response = new HashMap<>();
        response.put("employeeId", id);
        response.put("employeeName", employee.getFirstName() + " " + employee.getLastName());
        response.put("totalPayment", payment);
        response.put("dailyHours", dailyHours);
        response.put("hourlyRate", 30.0);
        response.put("overtimeRate", 42.0);
        response.put("reportPeriod", "Last 14 days");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/hours/{id}")
    public ResponseEntity<Map<String, Object>> getDailyHours(@PathVariable Integer id) {
        if (!employeeService.employeeExists(id)) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Double> dailyHours = reportService.calculateDailyHours(id);

        Map<String, Object> response = new HashMap<>();
        response.put("employeeId", id);
        response.put("dailyHours", dailyHours);

        return ResponseEntity.ok(response);
    }
}