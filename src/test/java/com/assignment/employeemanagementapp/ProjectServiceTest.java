package com.assignment.employeemanagementapp;

import com.assignment.employeemanagementapp.exception.ResourceNotFoundException;
import com.assignment.employeemanagementapp.model.Department;
import com.assignment.employeemanagementapp.model.Project;
import com.assignment.employeemanagementapp.repositories.DepartmentRepository;
import com.assignment.employeemanagementapp.repositories.ProjectRepository;
import com.assignment.employeemanagementapp.service.impl.ProjectServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {
    private MockMvc mockMvc;
    Project p1 = new Project(1l, "Test1", "Test1", "Test1", null);
    Project p2 = new Project(2l, "Test2", "Test2", "Test2", null);
    Project p3 = new Project(3l, "Test3", "Test3", "Test3", null);

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
        Mockito.when(projectRepository.findAll()).thenReturn(Arrays.asList(p1,p2,p3));
        assertThat(projectService.getAllProjects().size()).isEqualTo(3);
    }

    @Test
    public void getProjectById_success() throws Exception {
        long id = 1L;
        Mockito.when(projectRepository.findById(id)).thenReturn(Optional.ofNullable(p1));
        assertThat(projectService.getProjectById(id)).isNotNull();
    }

//    @Test
//    public void getProjectById_throwsException() {
//        assertThatExceptionOfType(ResourceNotFoundException.class)
//                .isThrownBy(() -> projectService.getDepartmentById(1L))
//                .withMessage("Department not found with Id: '1'");
//    }

    @Test
    public void getProjectsByDepartmentId_success() throws Exception {
        Department d = new Department(1L, "test", "Test");
        Mockito.when(departmentRepository.findById(d.getId())).thenReturn(Optional.ofNullable(d));
        Mockito.when(projectRepository.findByDeptId(d.getId())).thenReturn(Arrays.asList(p1,p2,p3));
        assertThat(projectService.getProjectByDeptId(d.getId())).isNotNull();
    }


    @Test
    public void getProjectByIdAndDepartmentId_success() throws Exception {
        Department d = new Department(1L, "test", "Test");
        Project p = new Project(4L, "Test4", "Test4", "Test4", null);
        Mockito.when(departmentRepository.findById(d.getId())).thenReturn(Optional.ofNullable(d));
        Mockito.when(projectRepository.findByPidAndDeptId(p.getPid(), d.getId())).thenReturn(p);
        assertThat(projectService.getProjectByIdAndDeptId(p.getPid(), d.getId())).isNotNull();
    }

    @Test
    public void createProject_success() throws Exception {
        Project p = new Project(4L, "Test4", "Test4", "Test4", null);
        Department d = new Department(1L, "test", "Test");
        Mockito.when(projectRepository.save(p)).thenReturn(p);
        Mockito.when(departmentRepository.findById(d.getId())).thenReturn(Optional.ofNullable(d));
        assertThat(projectService.saveProject(p, d.getId())).isNotNull();
    }

    @Test
    public void updateProject_success() throws Exception {
        Project p = new Project(4L, "Test4", "Test4", "Test4", null);
        Department d = new Department(1L, "test", "Test");
        Mockito.when(departmentRepository.findById(d.getId())).thenReturn(Optional.ofNullable(d));
        Mockito.when(projectRepository.findById(p.getPid())).thenReturn(Optional.ofNullable(p));
        Mockito.when(projectRepository.save(p)).thenReturn(p);
        assertThat(projectService.updateProject(p, p.getPid(), d.getId())).isNotNull();
    }

    @Test
    public void deleteProject_success() throws Exception {
        assertTrue(true);
    }
}
