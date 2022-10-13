package com.assignment.employeemanagementapp.controller;

import com.assignment.employeemanagementapp.model.Department;
import com.assignment.employeemanagementapp.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private DepartmentService deptservice;

    public DepartmentController(DepartmentService deptservice) {
        this.deptservice = deptservice;
    }

    // build create Department REST API
    @PostMapping()
    public ResponseEntity<Department> saveDepartment(@RequestBody Department d) {
        return new ResponseEntity<>(deptservice.saveDepartment(d), HttpStatus.CREATED);
    }

    // build get all departments RESt API
    @GetMapping()
    public List<Department> getAllDepartments(){
        return deptservice.getAllDepartments();
    }

    // build get department by id REST ID
    @GetMapping("{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") long id){
        return new ResponseEntity<>(deptservice.getDepartmentById(id), HttpStatus.OK);
    }

    // build update department REST API
    @PutMapping("{id}")
    public ResponseEntity<Department> updateDepartment(@RequestBody Department d, @PathVariable("id") long id){
        return new ResponseEntity<>(deptservice.updateDepartment(d, id), HttpStatus.OK);
    }

    // build delete department REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable("id") long id){
        deptservice.deleteDepartment(id);
        return new ResponseEntity<>("Department deleted successfully!", HttpStatus.OK);
    }
}
