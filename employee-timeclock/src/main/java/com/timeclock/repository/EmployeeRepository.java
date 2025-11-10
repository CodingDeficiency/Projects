package com.timeclock.repository;

import com.timeclock.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Find employee by first name and last name
    Optional<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    // Find employee by email
    Optional<Employee> findByEmail(String email);

    // Custom query to get max employee ID
    @Query("SELECT COALESCE(MAX(e.id), 0) FROM Employee e")
    Integer findMaxEmployeeId();
}