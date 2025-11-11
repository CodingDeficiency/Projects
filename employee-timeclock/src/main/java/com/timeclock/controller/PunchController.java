package com.timeclock.controller;

import com.timeclock.model.PunchRecord;
import com.timeclock.service.EmployeeService;
import com.timeclock.service.PunchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/punch")
@CrossOrigin(origins = "*")
public class PunchController {

    @Autowired
    private PunchService punchService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/in/{id}")
    public ResponseEntity<Map<String, Object>> punchIn(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        if (!employeeService.employeeExists(id)) {
            response.put("success", false);
            response.put("message", "Employee not found");
            return ResponseEntity.notFound().build();
        }

        boolean success = punchService.punchIn(id);

        response.put("success", success);
        response.put("message", success ? "Punched in successfully!" : "Failed to punch in. Already punched in?");
        response.put("timestamp", new Date());
        response.put("employeeId", id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/out/{id}")
    public ResponseEntity<Map<String, Object>> punchOut(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        if (!employeeService.employeeExists(id)) {
            response.put("success", false);
            response.put("message", "Employee not found");
            return ResponseEntity.notFound().build();
        }

        boolean success = punchService.punchOut(id);

        response.put("success", success);
        response.put("message", success ? "Punched out successfully!" : "Failed to punch out. Not punched in?");
        response.put("timestamp", new Date());
        response.put("employeeId", id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/records/{id}")
    public ResponseEntity<List<PunchRecord>> getEmployeePunchRecords(@PathVariable Integer id) {
        if (!employeeService.employeeExists(id)) {
            return ResponseEntity.notFound().build();
        }

        List<PunchRecord> records = punchService.getEmployeePunchRecords(id);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<Map<String, Object>> getPunchStatus(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();

        if (!employeeService.employeeExists(id)) {
            return ResponseEntity.notFound().build();
        }

        boolean canPunchOut = punchService.canPunchOut(id);

        response.put("employeeId", id);
        response.put("canPunchOut", canPunchOut);
        response.put("status", canPunchOut ? "Punched In" : "Punched Out");

        return ResponseEntity.ok(response);
    }
}