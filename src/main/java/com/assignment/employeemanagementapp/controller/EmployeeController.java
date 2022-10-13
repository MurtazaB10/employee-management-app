package com.assignment.employeemanagementapp.controller;

import com.assignment.employeemanagementapp.model.Employee;
import com.assignment.employeemanagementapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService empservice;

    // build create employee REST API
    @PostMapping("{did}")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee e, @PathVariable("did") long dept_id){
        return new ResponseEntity<Employee>(empservice.saveEmployee(e, dept_id), HttpStatus.CREATED);
    }

    // build get all employee REST API
    @GetMapping()
    public ResponseEntity<List<Employee>> getAllEmployees(){
        return new ResponseEntity<>(empservice.getAllEmployees(), HttpStatus.OK);
    }

    // build get employee by id REST API
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id){
        return new ResponseEntity<Employee>(empservice.getEmployeeById(id), HttpStatus.OK);
    }

    // build get employees by department id REST API
    @GetMapping("/department/{did}")
    public List<Employee> getEmployeeByDeptId(@PathVariable("did") long id){
        return empservice.getEmployeeByDeptId(id);
    }

    // build get employees by department id and employee id REST API
    @GetMapping("{eid}/department/{did}")
    public ResponseEntity<Employee> getEmployeeByIdAndDeptId(@PathVariable("eid") long id, @PathVariable("did") long dept_id){
        return new ResponseEntity<>(empservice.getEmployeeByIdAndDeptId(id, dept_id), HttpStatus.OK);
    }

    // build update employee REST API
    @PutMapping("{eid}/department/{did}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee e, @PathVariable("eid") long id, @PathVariable("did") long dept_id){
        return new ResponseEntity<Employee>(empservice.updateEmployee(e,id, dept_id), HttpStatus.OK);
    }

    // build assign employee project REST API
    @PutMapping("{eid}/project/{pid}")
    public ResponseEntity<Employee> assignProjectsToEmployee(@PathVariable long eid, @PathVariable long pid){
        return new ResponseEntity<>(empservice.assignProjectsToEmployee(eid, pid), HttpStatus.OK);
    }

    // build delete Employee REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") long id){
        empservice.deleteEmployee(id);
        return new ResponseEntity<String>("Employee deleted successfully!", HttpStatus.OK);
    }

}
