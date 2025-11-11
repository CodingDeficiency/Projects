package com.timeclock.service;

import com.timeclock.model.Employee;
import com.timeclock.model.PunchRecord;
import com.timeclock.repository.EmployeeRepository;
import com.timeclock.repository.PunchRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportService {

    private static final double HOURLY_RATE = 30.0;
    private static final double OVERTIME_MULTIPLIER = 1.4;
    private static final int REGULAR_HOURS_PER_DAY = 8;

    @Autowired
    private PunchRecordRepository punchRecordRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public void generateIndividualReport(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            System.out.println("❌ Employee not found!");
            return;
        }

        Date endDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        cal.add(Calendar.DAY_OF_MONTH, -14);
        Date startDate = cal.getTime();

        List<PunchRecord> records = punchRecordRepository.findByEmployeeIdAndDateRange(
                employeeId, startDate, endDate);

        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║         EMPLOYEE WORK REPORT                   ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.println("Employee ID: " + employee.getId());
        System.out.println("Name: " + employee.getFirstName() + " " + employee.getLastName());
        System.out.println("Email: " + employee.getEmail());
        System.out.println("Report Period: Last 14 Days");
        System.out.println("════════════════════════════════════════════════\n");

        Map<String, List<PunchRecord>> dailyRecords = groupRecordsByDate(records);
        double totalHours = 0.0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd (EEEE)");

        for (Map.Entry<String, List<PunchRecord>> entry : dailyRecords.entrySet()) {
            List<PunchRecord> dayRecords = entry.getValue();
            double hoursWorked = calculateHoursForDay(dayRecords);
            totalHours += hoursWorked;

            if (!dayRecords.isEmpty() && hoursWorked > 0) {
                Date date = dayRecords.get(0).getPunchTime();
                System.out.printf("%-30s: %.2f hours%n", sdf.format(date), hoursWorked);
            }
        }

        System.out.println("────────────────────────────────────────────────");
        System.out.printf("TOTAL HOURS (2 weeks): %.2f hours%n", totalHours);
        System.out.printf("TOTAL PAYMENT: $%.2f%n", calculatePayment(employeeId));
        System.out.println("════════════════════════════════════════════════\n");
    }

    public void generateAllEmployeesReport() {
        Date endDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        cal.add(Calendar.DAY_OF_MONTH, -14);
        Date startDate = cal.getTime();

        List<Employee> employees = employeeRepository.findAll();

        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║         ALL EMPLOYEES REPORT                   ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.println("Report Period: Last 14 Days");
        System.out.println("════════════════════════════════════════════════\n");

        double totalPayroll = 0.0;

        System.out.printf("%-5s %-20s %-15s %-15s%n", "ID", "Name", "Hours", "Payment");
        System.out.println("────────────────────────────────────────────────");

        for (Employee employee : employees) {
            List<PunchRecord> records = punchRecordRepository.findByEmployeeIdAndDateRange(
                    employee.getId(), startDate, endDate);

            double totalHours = 0.0;
            Map<String, List<PunchRecord>> dailyRecords = groupRecordsByDate(records);

            for (List<PunchRecord> dayRecords : dailyRecords.values()) {
                totalHours += calculateHoursForDay(dayRecords);
            }

            double payment = calculatePaymentForEmployee(employee.getId());
            totalPayroll += payment;

            String fullName = employee.getFirstName() + " " + employee.getLastName();
            System.out.printf("%-5d %-20s %-15.2f $%-14.2f%n",
                    employee.getId(),
                    fullName.length() > 20 ? fullName.substring(0, 17) + "..." : fullName,
                    totalHours,
                    payment);
        }
        System.out.println("────────────────────────────────────────────────");
        System.out.printf("TOTAL PAYROLL: $%.2f%n", totalPayroll);
        System.out.println("════════════════════════════════════════════════\n");
    }

    public double calculatePayment(Integer employeeId) {
        return calculatePaymentForEmployee(employeeId);
    }

    private double calculatePaymentForEmployee(Integer employeeId) {
        Date endDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        cal.add(Calendar.DAY_OF_MONTH, -14);
        Date startDate = cal.getTime();

        List<PunchRecord> records = punchRecordRepository.findByEmployeeIdAndDateRange(
                employeeId, startDate, endDate);

        Map<String, List<PunchRecord>> dailyRecords = groupRecordsByDate(records);
        double totalPayment = 0.0;

        for (List<PunchRecord> dayRecords : dailyRecords.values()) {
            double hoursWorked = calculateHoursForDay(dayRecords);

            if (hoursWorked <= REGULAR_HOURS_PER_DAY) {
                totalPayment += hoursWorked * HOURLY_RATE;
            } else {
                double regularPay = REGULAR_HOURS_PER_DAY * HOURLY_RATE;
                double overtimeHours = hoursWorked - REGULAR_HOURS_PER_DAY;
                double overtimePay = overtimeHours * HOURLY_RATE * OVERTIME_MULTIPLIER;
                totalPayment += regularPay + overtimePay;
            }
        }

        return totalPayment;
    }

    private Map<String, List<PunchRecord>> groupRecordsByDate(List<PunchRecord> records) {
        Map<String, List<PunchRecord>> grouped = new LinkedHashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (PunchRecord record : records) {
            String dateKey = sdf.format(record.getPunchTime());
            grouped.computeIfAbsent(dateKey, k -> new ArrayList<>()).add(record);
        }

        return grouped;
    }

    private double calculateHoursForDay(List<PunchRecord> dayRecords) {
        double totalHours = 0.0;
        PunchRecord punchIn = null;

        for (PunchRecord record : dayRecords) {
            if (record.getPunchType() == 'I') {
                punchIn = record;
            } else if (record.getPunchType() == 'O' && punchIn != null) {
                long diffMillis = record.getPunchTime().getTime() - punchIn.getPunchTime().getTime();
                double hours = diffMillis / (1000.0 * 60 * 60);
                totalHours += hours;
                punchIn = null;
            }
        }

        return totalHours;
    }

    public Map<String, Double> calculateDailyHours(Integer employeeId) {
        Date endDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        cal.add(Calendar.DAY_OF_MONTH, -14);
        Date startDate = cal.getTime();

        List<PunchRecord> records = punchRecordRepository.findByEmployeeIdAndDateRange(
                employeeId, startDate, endDate);

        Map<String, List<PunchRecord>> dailyRecords = groupRecordsByDate(records);
        Map<String, Double> dailyHours = new LinkedHashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Map.Entry<String, List<PunchRecord>> entry : dailyRecords.entrySet()) {
            List<PunchRecord> dayRecords = entry.getValue();
            double hours = calculateHoursForDay(dayRecords);
            if (!dayRecords.isEmpty()) {
                String dateStr = sdf.format(dayRecords.get(0).getPunchTime());
                dailyHours.put(dateStr, hours);
            }
        }

        return dailyHours;
    }
}