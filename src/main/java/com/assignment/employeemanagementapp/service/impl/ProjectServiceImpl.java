package com.assignment.employeemanagementapp.service.impl;

import com.assignment.employeemanagementapp.exception.ResourceNotFoundException;
import com.assignment.employeemanagementapp.model.Department;
import com.assignment.employeemanagementapp.model.Project;
import com.assignment.employeemanagementapp.repositories.DepartmentRepository;
import com.assignment.employeemanagementapp.repositories.ProjectRepository;
import com.assignment.employeemanagementapp.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository pr;
    private DepartmentRepository dr;

    public ProjectServiceImpl(ProjectRepository pr, DepartmentRepository dr) {
        this.pr = pr;
        this.dr = dr;
    }

    @Override
    public Project saveProject(Project p, long id) {
        return dr.findById(id).map(dept -> {
            p.setDept(dept);
            return pr.save(p);
        }).orElseThrow(() -> new ResourceNotFoundException("Department", "Id", id));
    }

    @Override
    public List<Project> getAllProjects() {
        return pr.findAll();
    }

    @Override
    public Project getProjectById(long id) {
        return pr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project", "Id", id));
    }

    @Override
    public List<Project> getProjectByDeptId(long id) {
        dr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department", "Id", id));
        return pr.findByDeptId(id);
    }

    @Override
    public Project getProjectByIdAndDeptId(long id, long dept_id) {
        dr.findById(dept_id).orElseThrow(() -> new ResourceNotFoundException("Department", "Id", dept_id));
        pr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project", "Id", id));
        return pr.findByPidAndDeptId(id, dept_id);
    }

    @Override
    public Project updateProject(Project p, long id, long dept_id) {
        Project exists = pr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project", "Id", id));
        Department d = dr.findById(dept_id).orElseThrow(() -> new ResourceNotFoundException("Department", "Id", dept_id));
        exists.setPname(p.getPname());
        exists.setPdescription(p.getPdescription());
        exists.setPtechnology(p.getPtechnology());
        exists.setDept(d);
        pr.save(exists);
        return exists;
    }

    @Override
    public void deleteProject(long id) {
        pr.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project", "Id", id));
        pr.deleteById(id);
    }
}
