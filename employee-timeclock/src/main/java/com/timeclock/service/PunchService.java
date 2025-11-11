package com.timeclock.service;

import com.timeclock.model.Employee;
import com.timeclock.model.PunchRecord;
import com.timeclock.repository.EmployeeRepository;
import com.timeclock.repository.PunchRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PunchService {

    @Autowired
    private PunchRecordRepository punchRecordRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean punchIn(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            System.out.println("❌ Employee not found!");
            return false;
        }

        PunchRecord lastPunch = getLastPunchToday(employeeId);
        if (lastPunch != null && lastPunch.getPunchType() == 'I') {
            System.out.println("❌ Error: Already punched in! Must punch out first.");
            return false;
        }

        PunchRecord punchRecord = new PunchRecord(employee, 'I');
        punchRecordRepository.save(punchRecord);
        System.out.println("✅ Employee " + employeeId + " punched in at " + new Date());
        return true;
    }

    public boolean punchOut(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            System.out.println("❌ Employee not found!");
            return false;
        }

        if (!canPunchOut(employeeId)) {
            System.out.println("❌ Error: Must punch in before punching out!");
            return false;
        }

        PunchRecord punchRecord = new PunchRecord(employee, 'O');
        punchRecordRepository.save(punchRecord);
        System.out.println("✅ Employee " + employeeId + " punched out at " + new Date());
        return true;
    }

    public boolean canPunchOut(Integer employeeId) {
        PunchRecord lastPunch = getLastPunchToday(employeeId);
        return lastPunch != null && lastPunch.getPunchType() == 'I';
    }

    private PunchRecord getLastPunchToday(Integer employeeId) {
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startOfDay = cal.getTime();

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Date endOfDay = cal.getTime();

        List<PunchRecord> records = punchRecordRepository.findLastPunchForEmployeeOnDate(
                employeeId, startOfDay, endOfDay);

        return records.isEmpty() ? null : records.get(0);
    }

    public List<PunchRecord> getEmployeePunchRecords(Integer employeeId) {
        return punchRecordRepository.findByEmployeeIdOrderByPunchTimeAsc(employeeId);
    }
}