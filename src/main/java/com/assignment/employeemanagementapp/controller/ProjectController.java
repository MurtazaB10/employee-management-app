package com.assignment.employeemanagementapp.controller;

import com.assignment.employeemanagementapp.model.Project;
import com.assignment.employeemanagementapp.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // build create project REST API
    @PostMapping("{did}")
    public ResponseEntity<Project> saveProject(@RequestBody Project p, @PathVariable("did") long id){
        return new ResponseEntity<>(projectService.saveProject(p, id), HttpStatus.ACCEPTED);
    }

    // build get all projects REST API
    @GetMapping()
    public List<Project> getAllProjects(){
        return projectService.getAllProjects();
    }

    // build get project by id REST API
    @GetMapping("{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable("id") long id){
        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }

    // build get project by department id REST API
    @GetMapping("department/{did}")
    public List<Project> getProjectByDeptId(@PathVariable("did") long id){
        return projectService.getProjectByDeptId(id);
    }

    // build get project by pid and department id REST API
    @GetMapping("{eid}/department/{did}")
    public ResponseEntity<Project> getProjectByPidAndDeptId(@PathVariable("eid") long id, @PathVariable("did") long dept_id){
        return new ResponseEntity<>(projectService.getProjectByIdAndDeptId(id, dept_id), HttpStatus.OK);
    }

    // build update project REST API
    @PatchMapping("{eid}/department/{did}")
    public ResponseEntity<Project> updateProject(@RequestBody Project p, @PathVariable("eid") long id, @PathVariable("did") long dept_id){
        return new ResponseEntity<>(projectService.updateProject(p, id, dept_id), HttpStatus.OK);
    }

    // build delete project REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProject(@PathVariable("id") long id){
        projectService.deleteProject(id);
        return new ResponseEntity<>("Project deleted successfully!", HttpStatus.OK);
    }
}
