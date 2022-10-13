package com.assignment.employeemanagementapp.repositories;

import com.assignment.employeemanagementapp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDeptId(Long id);
    Employee findByIdAndDeptId(Long id, Long dept_id);
}
