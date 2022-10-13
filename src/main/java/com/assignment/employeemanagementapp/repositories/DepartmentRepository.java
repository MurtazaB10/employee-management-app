package com.assignment.employeemanagementapp.repositories;

import com.assignment.employeemanagementapp.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
