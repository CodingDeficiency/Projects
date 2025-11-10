package com.timeclock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "punch_record")
public class PunchRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonIgnore // Prevents circular reference
    private Employee employee;

    @Column(name = "punch_type", nullable = false, length = 1)
    private Character punchType; // 'I' for In, 'O' for Out

    @Column(name = "punch_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date punchTime;

    @Column(name = "day_of_week", length = 10)
    private String dayOfWeek;

    // Constructors
    public PunchRecord() {
        this.punchTime = new Date();
        this.dayOfWeek = calculateDayOfWeek();
    }

    public PunchRecord(Employee employee, Character punchType) {
        this.employee = employee;
        this.punchType = punchType;
        this.punchTime = new Date();
        this.dayOfWeek = calculateDayOfWeek();
    }

    private String calculateDayOfWeek() {
        String[] days = { "Sunday", "Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday" };
        Calendar cal = Calendar.getInstance();
        if (punchTime != null) {
            cal.setTime(punchTime);
        }
        return days[cal.get(Calendar.DAY_OF_WEEK) - 1];
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Character getPunchType() {
        return punchType;
    }

    public void setPunchType(Character punchType) {
        this.punchType = punchType;
    }

    public Date getPunchTime() {
        return punchTime;
    }

    public void setPunchTime(Date punchTime) {
        this.punchTime = punchTime;
        this.dayOfWeek = calculateDayOfWeek();
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    // Helper method to get employee ID for JSON
    @Transient
    public Integer getEmployeeId() {
        return employee != null ? employee.getId() : null;
    }

    @Override
    public String toString() {
        return "PunchRecord{" +
                "id=" + id +
                ", employeeId=" + (employee != null ? employee.getId() : "null") +
                ", punchType=" + punchType +
                ", punchTime=" + punchTime +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                '}';
    }
}
