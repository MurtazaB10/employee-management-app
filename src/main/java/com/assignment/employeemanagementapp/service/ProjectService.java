package com.assignment.employeemanagementapp.service;

import com.assignment.employeemanagementapp.model.Employee;
import com.assignment.employeemanagementapp.model.Project;

import java.util.List;

public interface ProjectService {

    Project saveProject(Project e, long dept_id);
    List<Project> getAllProjects();
    Project getProjectById(long id);
    List<Project> getProjectByDeptId(long id);
    Project getProjectByIdAndDeptId(long id, long dept_id);
    Project updateProject(Project e, long id, long dept_id);
    void deleteProject(long id);
}
