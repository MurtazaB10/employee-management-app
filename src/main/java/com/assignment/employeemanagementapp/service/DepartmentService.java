package com.assignment.employeemanagementapp.service;

import com.assignment.employeemanagementapp.model.Department;

import java.util.List;

public interface DepartmentService {
    Department saveDepartment(Department e);
    List<Department> getAllDepartments();
    Department getDepartmentById(long id);
    Department updateDepartment(Department e, long id);
    void deleteDepartment(long id);
}
