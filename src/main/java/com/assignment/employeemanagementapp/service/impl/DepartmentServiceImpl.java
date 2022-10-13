package com.assignment.employeemanagementapp.service.impl;

import com.assignment.employeemanagementapp.exception.ResourceNotFoundException;
import com.assignment.employeemanagementapp.model.Department;
import com.assignment.employeemanagementapp.repositories.DepartmentRepository;
import com.assignment.employeemanagementapp.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository dr;

    public DepartmentServiceImpl(DepartmentRepository dr) {
        this.dr = dr;
    }

    @Override
    public Department saveDepartment(Department d) {
        return dr.save(d);
    }

    @Override
    public List<Department> getAllDepartments() {
        return dr.findAll();
    }

    @Override
    public Department getDepartmentById(long id) {
        return dr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department", "Id", id));
    }

    @Override
    public Department updateDepartment(Department e, long id) {
        Department exists = dr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department", "Id", id));
        exists.setDname(e.getDname());
        exists.setDlocation(e.getDlocation());
        dr.save(exists);
        return exists;
    }

    @Override
    public void deleteDepartment(long id) {
        dr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department", "Id", id));
        dr.deleteById(id);
    }
}
