package com.assignment.employeemanagementapp.repositories;

import com.assignment.employeemanagementapp.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByDeptId(Long id);
    Project findByPidAndDeptId(Long id, Long dept_id);
}
