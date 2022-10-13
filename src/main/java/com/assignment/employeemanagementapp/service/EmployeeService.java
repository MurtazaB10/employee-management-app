package com.assignment.employeemanagementapp.service;

import com.assignment.employeemanagementapp.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee e, long dept_id);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(long id);
    List<Employee> getEmployeeByDeptId(long id);
    Employee getEmployeeByIdAndDeptId(long id, long dept_id);
    Employee updateEmployee(Employee e, long id, long dept_id);
    void deleteEmployee(long id);

    Employee assignProjectsToEmployee(long eid, long pid);
}
