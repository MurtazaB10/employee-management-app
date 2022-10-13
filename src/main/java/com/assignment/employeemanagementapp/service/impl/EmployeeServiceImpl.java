package com.assignment.employeemanagementapp.service.impl;

import com.assignment.employeemanagementapp.exception.ResourceNotFoundException;
import com.assignment.employeemanagementapp.model.Department;
import com.assignment.employeemanagementapp.model.Employee;
import com.assignment.employeemanagementapp.model.Project;
import com.assignment.employeemanagementapp.repositories.DepartmentRepository;
import com.assignment.employeemanagementapp.repositories.EmployeeRepository;
import com.assignment.employeemanagementapp.repositories.ProjectRepository;
import com.assignment.employeemanagementapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository er;
    @Autowired
    private DepartmentRepository dr;

    @Autowired
    private ProjectRepository pr;

    @Override
    public Employee saveEmployee(Employee e, long dept_id) {
        return dr.findById(dept_id).map(dept -> {
            e.setDept(dept);
            return er.save(e);
        }).orElseThrow(() -> new ResourceNotFoundException("Department", "Id", dept_id));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return er.findAll();
    }

    @Override
    public Employee getEmployeeById(long id) {
//        Optional<Employee> e = er.findById(id);
//        if(e.isPresent())
//            return e.get();
//        throw new ResourceNotFoundException("Employee", "Id", id);
        return er.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", id));
    }

    @Override
    public List<Employee> getEmployeeByDeptId(long id) {
        dr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department", "Id", id));
        return er.findByDeptId(id);
    }

    @Override
    public Employee getEmployeeByIdAndDeptId(long id, long dept_id) {
        dr.findById(dept_id).orElseThrow(() -> new ResourceNotFoundException("Department", "Id", dept_id));
        return er.findByIdAndDeptId(id, dept_id);
    }

    @Override
    public Employee updateEmployee(Employee e, long id, long dept_id) {
        // check whether the employee exists or not
        Employee exists = er.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", id));
        Department d = dr.findById(dept_id).orElseThrow(() -> new ResourceNotFoundException("Department", "Id", dept_id));
        exists.setFirstName(e.getFirstName());
        exists.setLastName(e.getLastName());
        exists.setEmail(e.getEmail());
        exists.setDept(d);
        er.save(exists);
        return exists;
    }

    @Override
    public void deleteEmployee(long id) {
        // check whether the employee exists
        Employee exists = er.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "Id", id));
        er.deleteById(id);
    }

    @Override
    public Employee assignProjectsToEmployee(long eid, long pid) {
        Set<Project> projects = null;
        Employee e = er.findById(eid).get();
        Project p = pr.findById(pid).get();
        projects = e.getAssignedProjects();
        projects.add(p);
        e.setAssignedProjects(projects);
        return er.save(e);
    }
}
