package com.timeclock;

import com.timeclock.model.Employee;
import com.timeclock.service.EmployeeService;
import com.timeclock.service.PunchService;
import com.timeclock.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PunchService punchService;

    @Autowired
    private ReportService reportService;

    private Scanner scanner = new Scanner(System.in);
    private int failedAttempts = 0;
    private static final int MAX_FAILED_ATTEMPTS = 3;

    @Override
    public void run(String... args) throws Exception {
        // Display startup message
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║   TIME CLOCK SYSTEM - DUAL MODE 🕐            ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("✅ Console Interface: Active");
        System.out.println("✅ Web Interface: http://localhost:8080");
        System.out.println("✅ API Endpoint: http://localhost:8080/api");
        System.out.println();
        System.out.println("💡 TIP: Open your browser and go to http://localhost:8080");
        System.out.println("    for a beautiful web interface!");
        System.out.println();

        // Ask user if they want to use console or just web
        System.out.print("Do you want to use the console interface? (y/n): ");
        String useConsole = scanner.nextLine().trim().toLowerCase();

        if (!useConsole.equals("y")) {
            System.out.println("\n👍 Great! The web interface is running at http://localhost:8080");
            System.out.println("   Press Ctrl+C to stop the server when you're done.");
            System.out.println();

            // Keep the application running (web server stays active)
            Thread.currentThread().join();
            return;
        }

        // Console interface
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║   WELCOME TO CONSOLE INTERFACE 🖥️             ║");
        System.out.println("╚════════════════════════════════════════════════╝\n");

        boolean running = true;

        while (running) {
            displayMainMenu();
            String choice = scanner.nextLine().toUpperCase().trim();

            switch (choice) {
                case "A":
                    addNewEmployee();
                    failedAttempts = 0;
                    break;
                case "B":
                    punchInOut();
                    failedAttempts = 0;
                    break;
                case "C":
                    displayReportMenu();
                    failedAttempts = 0;
                    break;
                case "D":
                    updateEmployee();
                    failedAttempts = 0;
                    break;
                case "E":
                    deleteEmployee();
                    failedAttempts = 0;
                    break;
                case "F":
                    calculateEmployeePayment();
                    failedAttempts = 0;
                    break;
                case "G":
                    System.out.println("\n👋 Exiting console interface...");
                    System.out.println("💡 Web interface is still running at http://localhost:8080");
                    System.out.println("   Press Ctrl+C to stop the server completely.");
                    System.out.println();
                    running = false;
                    break;
                default:
                    failedAttempts++;
                    System.out.println("\n❌ Invalid option! Please try again.");
                    if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
                        System.out.println("\n⚠️  Too many failed attempts. Exiting console...\n");
                        running = false;
                    }
                    break;
            }
        }

        // Keep web server running
        Thread.currentThread().join();
    }

    private void displayMainMenu() {
        System.out.println("\n════════════════════════════════════════════════");
        System.out.println("                 MAIN MENU");
        System.out.println("════════════════════════════════════════════════");
        System.out.println("A) Add New Employee");
        System.out.println("B) Punch In/Out");
        System.out.println("C) Report");
        System.out.println("D) Update Employee Details");
        System.out.println("E) Delete Employee");
        System.out.println("F) Calculate Payment for Employee");
        System.out.println("G) Exit Console (Web stays active)");
        System.out.println("════════════════════════════════════════════════");
        System.out.print("Choice: ");
    }

    private void addNewEmployee() {
        System.out.println("\n════════════════════════════════════════════════");
        System.out.println("            ADD NEW EMPLOYEE");
        System.out.println("════════════════════════════════════════════════");

        boolean addingEmployees = true;

        while (addingEmployees) {
            System.out.print("First Name: ");
            String firstName = scanner.nextLine().trim();

            System.out.print("Last Name: ");
            String lastName = scanner.nextLine().trim();

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            if (employeeService.validateEmployeeData(firstName, lastName, email)) {
                Integer employeeId = employeeService.addEmployee(firstName, lastName, email);
                System.out.println("\n✅ Employee added successfully!");
                System.out.println("📋 Employee ID: " + employeeId);
                System.out.println("👤 Name: " + firstName + " " + lastName);
                System.out.println("📧 Email: " + email);
            } else {
                System.out.println("\n❌ Invalid employee data! Please ensure all fields are filled correctly.");
            }

            System.out.print("\nDo you want to add another employee? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();
            addingEmployees = response.equals("y");
        }
    }

    private void punchInOut() {
        System.out.println("\n════════════════════════════════════════════════");
        System.out.println("              PUNCH IN/OUT");
        System.out.println("════════════════════════════════════════════════");

        System.out.print("Enter Employee ID: ");
        try {
            Integer employeeId = Integer.parseInt(scanner.nextLine().trim());

            Employee employee = employeeService.getEmployee(employeeId);
            if (employee == null) {
                System.out.println("\n❌ Employee not found!");
                pressAnyKeyToContinue();
                return;
            }

            System.out.println("\n📋 Employee: " + employee.getFirstName() + " " + employee.getLastName());
            System.out.print("Punch (I)n or (O)ut? ");
            String punchType = scanner.nextLine().trim().toUpperCase();

            if (punchType.equals("I")) {
                if (punchService.punchIn(employeeId)) {
                    System.out.println("\n✅ Successfully PUNCHED IN!");
                } else {
                    System.out.println("\n❌ Could not punch in!");
                }
            } else if (punchType.equals("O")) {
                if (punchService.punchOut(employeeId)) {
                    System.out.println("\n✅ Successfully PUNCHED OUT!");
                } else {
                    System.out.println("\n❌ Could not punch out!");
                }
            } else {
                System.out.println("\n❌ Invalid punch type! Please enter 'I' or 'O'.");
            }

        } catch (NumberFormatException e) {
            System.out.println("\n❌ Invalid Employee ID format! Please enter a number.");
        }

        pressAnyKeyToContinue();
    }

    private void displayReportMenu() {
        System.out.println("\n════════════════════════════════════════════════");
        System.out.println("                 REPORTS");
        System.out.println("════════════════════════════════════════════════");
        System.out.println("I) Individual Employee Report");
        System.out.println("A) All Employees Report");
        System.out.println("════════════════════════════════════════════════");
        System.out.print("Choice: ");

        String choice = scanner.nextLine().trim().toUpperCase();

        if (choice.equals("I")) {
            generateIndividualReport();
        } else if (choice.equals("A")) {
            reportService.generateAllEmployeesReport();
            pressAnyKeyToContinue();
        } else {
            System.out.println("\n❌ Invalid choice!");
            pressAnyKeyToContinue();
        }
    }

    private void generateIndividualReport() {
        int attempts = 0;
        boolean success = false;

        while (attempts < MAX_FAILED_ATTEMPTS && !success) {
            System.out.print("\nEnter Employee ID: ");
            try {
                Integer employeeId = Integer.parseInt(scanner.nextLine().trim());
                Employee employee = employeeService.getEmployee(employeeId);

                if (employee != null) {
                    reportService.generateIndividualReport(employeeId);
                    success = true;
                } else {
                    attempts++;
                    System.out.println("\n❌ Employee not found!");
                    if (attempts < MAX_FAILED_ATTEMPTS) {
                        System.out.println(
                                "Please try again. (" + (MAX_FAILED_ATTEMPTS - attempts) + " attempts remaining)");
                    }
                }
            } catch (NumberFormatException e) {
                attempts++;
                System.out.println("\n❌ Invalid Employee ID format!");
                if (attempts < MAX_FAILED_ATTEMPTS) {
                    System.out
                            .println("Please try again. (" + (MAX_FAILED_ATTEMPTS - attempts) + " attempts remaining)");
                }
            }
        }

        if (!success) {
            System.out.println("\n⚠️  Maximum attempts reached. Returning to main menu...");
        }

        pressAnyKeyToContinue();
    }

    private void updateEmployee() {
        System.out.println("\n════════════════════════════════════════════════");
        System.out.println("            UPDATE EMPLOYEE");
        System.out.println("════════════════════════════════════════════════");

        System.out.print("Enter Employee ID: ");
        try {
            Integer employeeId = Integer.parseInt(scanner.nextLine().trim());
            Employee employee = employeeService.getEmployee(employeeId);

            if (employee == null) {
                System.out.println("\n❌ Employee not found!");
                pressAnyKeyToContinue();
                return;
            }

            System.out.println("\n📋 Current Details:");
            System.out.println("Name: " + employee.getFirstName() + " " + employee.getLastName());
            System.out.println("Email: " + employee.getEmail());

            System.out.print("\nNew First Name (press Enter to keep current): ");
            String firstName = scanner.nextLine().trim();
            if (firstName.isEmpty())
                firstName = employee.getFirstName();

            System.out.print("New Last Name (press Enter to keep current): ");
            String lastName = scanner.nextLine().trim();
            if (lastName.isEmpty())
                lastName = employee.getLastName();

            System.out.print("New Email (press Enter to keep current): ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty())
                email = employee.getEmail();

            if (employeeService.updateEmployee(employeeId, firstName, lastName, email)) {
                System.out.println("\n✅ Employee updated successfully!");
            } else {
                System.out.println("\n❌ Failed to update employee!");
            }

        } catch (NumberFormatException e) {
            System.out.println("\n❌ Invalid Employee ID format!");
        }

        pressAnyKeyToContinue();
    }

    private void deleteEmployee() {
        System.out.println("\n════════════════════════════════════════════════");
        System.out.println("            DELETE EMPLOYEE");
        System.out.println("════════════════════════════════════════════════");

        System.out.print("Enter Employee ID: ");
        try {
            Integer employeeId = Integer.parseInt(scanner.nextLine().trim());
            Employee employee = employeeService.getEmployee(employeeId);

            if (employee == null) {
                System.out.println("\n❌ Employee not found!");
                pressAnyKeyToContinue();
                return;
            }

            System.out.println("\n⚠️  Employee: " + employee.getFirstName() + " " + employee.getLastName());
            System.out.println("⚠️  This will also delete all punch records for this employee!");
            System.out.print("Are you sure you want to delete this employee? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (confirm.equals("y")) {
                if (employeeService.deleteEmployee(employeeId)) {
                    System.out.println("\n✅ Employee deleted successfully!");
                } else {
                    System.out.println("\n❌ Failed to delete employee!");
                }
            } else {
                System.out.println("\n❌ Deletion cancelled.");
            }

        } catch (NumberFormatException e) {
            System.out.println("\n❌ Invalid Employee ID format!");
        }

        pressAnyKeyToContinue();
    }

    private void calculateEmployeePayment() {
        System.out.println("\n════════════════════════════════════════════════");
        System.out.println("        CALCULATE EMPLOYEE PAYMENT");
        System.out.println("════════════════════════════════════════════════");

        System.out.print("Enter Employee ID: ");
        try {
            Integer employeeId = Integer.parseInt(scanner.nextLine().trim());
            Employee employee = employeeService.getEmployee(employeeId);

            if (employee == null) {
                System.out.println("\n❌ Employee not found!");
                pressAnyKeyToContinue();
                return;
            }

            double payment = reportService.calculatePayment(employeeId);
            System.out.println("\n📋 Employee: " + employee.getFirstName() + " " + employee.getLastName());
            System.out.printf("💰 Total Payment (Last 2 weeks): $%.2f%n", payment);
            System.out.println("\n💡 Payment Breakdown:");
            System.out.println("   • Regular hours (up to 8/day): $30/hour");
            System.out.println("   • Overtime (over 8 hours/day): $42/hour (40% premium)");

        } catch (NumberFormatException e) {
            System.out.println("\n❌ Invalid Employee ID format!");
        }

        pressAnyKeyToContinue();
    }

    private void pressAnyKeyToContinue() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}