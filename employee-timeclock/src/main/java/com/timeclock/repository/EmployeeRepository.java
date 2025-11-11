package com.timeclock.repository;

import com.timeclock.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<Employee> findByEmail(String email);

    @Query("SELECT COALESCE(MAX(e.id), 0) FROM Employee e")
    Integer findMaxEmployeeId();
}