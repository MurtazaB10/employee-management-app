package com.assignment.employeemanagementapp;

import com.assignment.employeemanagementapp.exception.ResourceNotFoundException;
import com.assignment.employeemanagementapp.model.Department;
import com.assignment.employeemanagementapp.model.Project;
import com.assignment.employeemanagementapp.repositories.DepartmentRepository;
import com.assignment.employeemanagementapp.repositories.ProjectRepository;
import com.assignment.employeemanagementapp.service.impl.ProjectServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProjectServiceTest {
    private MockMvc mockMvc;
    Project p1 = new Project(1l, "Test1", "Test1", "Test1", null);
    Project p2 = new Project(2l, "Test2", "Test2", "Test2", null);
    Project p3 = new Project(3l, "Test3", "Test3", "Test3", null);
    Department d = new Department(1L, "test", "Test");

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(projectService).build();
    }

    @Test
    public void getAllProjects_success() throws Exception {
        when(projectRepository.findAll()).thenReturn(Arrays.asList(p1,p2,p3));
        assertThat(projectService.getAllProjects().size()).isEqualTo(3);
    }

    @Test
    public void getProjectById_success() throws Exception {
        long id = 1L;
        when(projectRepository.findById(id)).thenReturn(Optional.ofNullable(p1));
        assertThat(projectService.getProjectById(id)).isNotNull();
    }

    @Test
    public void getProjectById_throwsException() {
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> projectService.getProjectById(1L))
                .withMessage("Project not found with Id: '1'");
    }

    @Test
    public void getProjectsByDepartmentId_success() throws Exception {
        when(departmentRepository.findById(d.getId())).thenReturn(Optional.ofNullable(d));
        when(projectRepository.findByDeptId(d.getId())).thenReturn(Arrays.asList(p1,p2,p3));
        assertThat(projectService.getProjectByDeptId(d.getId())).isNotNull();
    }

    @Test
    public void getProjectsByDepartmentId_throwsException() {
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> projectService.getProjectByDeptId(1L))
                .withMessage("Department not found with Id: '1'");
    }


    @Test
    public void getProjectByIdAndDepartmentId_success() throws Exception {
        Project p = new Project(4L, "Test4", "Test4", "Test4", null);
        when(departmentRepository.findById(d.getId())).thenReturn(Optional.ofNullable(d));
        when(projectRepository.findById(p.getPid())).thenReturn(Optional.ofNullable(p));
        when(projectRepository.findByPidAndDeptId(p.getPid(), d.getId())).thenReturn(p);
        assertThat(projectService.getProjectByIdAndDeptId(p.getPid(), d.getId())).isNotNull();
    }

    @Test
    public void getProjectByIdAndDepartmentId_throwsException() {
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> projectService.getProjectByIdAndDeptId(1L, 1L))
                .withMessage("Department not found with Id: '1'");
        when(departmentRepository.findById(1L)).thenReturn(Optional.ofNullable(d));
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> projectService.getProjectByIdAndDeptId(1L, 1L))
                .withMessage("Project not found with Id: '1'");
    }

    @Test
    public void createProject_success() throws Exception {
        when(projectRepository.save(p1)).thenReturn(p1);
        when(departmentRepository.findById(d.getId())).thenReturn(Optional.ofNullable(d));
        assertThat(projectService.saveProject(p1, d.getId())).isNotNull();
    }

    @Test
    public void createProject_throwsException() {
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> projectService.saveProject(p1, 1L))
                .withMessage("Department not found with Id: '1'");
    }

    @Test
    public void updateProject_success() throws Exception {
        when(departmentRepository.findById(d.getId())).thenReturn(Optional.ofNullable(d));
        when(projectRepository.findById(p1.getPid())).thenReturn(Optional.ofNullable(p1));
        when(projectRepository.save(p1)).thenReturn(p1);
        assertThat(projectService.updateProject(p1, p1.getPid(), d.getId())).isNotNull();
    }

    @Test
    public void updateProject_throwsException() {
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> projectService.updateProject(p1, 1L, 1L))
                .withMessage("Project not found with Id: '1'");
        when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(p1));
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> projectService.updateProject(p1, 1L, 1L))
                .withMessage("Department not found with Id: '1'");
    }

    @Test
    public void deleteProject_success() throws Exception {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(p1));
        projectService.deleteProject(1L);
        verify(projectRepository).findById(1L);
    }

    @Test
    public void deleteProject_throwsException() {
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> projectService.deleteProject(1L))
                .withMessage("Project not found with Id: '1'");
    }
}
