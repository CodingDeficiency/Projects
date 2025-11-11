package com.timeclock.repository;

import com.timeclock.model.PunchRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PunchRecordRepository extends JpaRepository<PunchRecord, Integer> {

    List<PunchRecord> findByEmployeeIdOrderByPunchTimeAsc(Integer employeeId);

    @Query("SELECT pr FROM PunchRecord pr WHERE pr.employee.id = :employeeId " +
            "AND pr.punchTime BETWEEN :startDate AND :endDate ORDER BY pr.punchTime")
    List<PunchRecord> findByEmployeeIdAndDateRange(
            @Param("employeeId") Integer employeeId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    @Query("SELECT pr FROM PunchRecord pr WHERE pr.employee.id = :employeeId " +
            "AND pr.punchTime BETWEEN :startDate AND :endDate " +
            "ORDER BY pr.punchTime DESC")
    List<PunchRecord> findLastPunchForEmployeeOnDate(
            @Param("employeeId") Integer employeeId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    @Query("SELECT pr FROM PunchRecord pr WHERE pr.punchTime BETWEEN :startDate AND :endDate " +
            "ORDER BY pr.employee.id, pr.punchTime")
    List<PunchRecord> findAllInDateRange(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);
}