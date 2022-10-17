package com.assignment.employeemanagementapp;

import com.assignment.employeemanagementapp.controller.ProjectController;
import com.assignment.employeemanagementapp.model.Department;
import com.assignment.employeemanagementapp.model.Project;
import com.assignment.employeemanagementapp.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectControllerTest {

    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    Project p1 = new Project(1L, "Test1", "Test1", "Test1", null);
    Project p2 = new Project(2L, "Test2", "Test2", "Test2", null);
    Project p3 = new Project(3L, "Test3", "Test3", "Test3", null);

    Department d = new Department(1L, "Test", "Test");

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @Test
    public void getAllProjects_success() throws Exception {
        List<Project> projects = new ArrayList<>(Arrays.asList(p1,p2,p3));
        Mockito.when(projectService.getAllProjects()).thenReturn(projects);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].pname", is("Test3")))
                .andExpect(jsonPath("$[1].pname", is("Test2")));
    }

    @Test
    public void getProjectById_success() throws Exception {
        Mockito.when(projectService.getProjectById(p1.getPid())).thenReturn(p1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/projects/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.pname", is("Test1")));
    }

    @Test
    public void getProjectsByDepartmentId_success() throws Exception {
        Mockito.when(projectService.getProjectByDeptId(d.getId())).thenReturn(Arrays.asList(p1,p2,p3));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/projects/department/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].pname", is("Test3")))
                .andExpect(jsonPath("$[1].pname", is("Test2")));
    }

    @Test
    public void getProjectByPidAndDepartmentId_success() throws Exception {
        Mockito.when(projectService.getProjectByIdAndDeptId(p1.getPid(), d.getId())).thenReturn(p1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/projects/1/department/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pname", is("Test1")));
    }

    @Test
    public void saveProject_success() throws Exception {
        when(projectService.saveProject(p1, 1L)).thenReturn(p1);
        String content = objectMapper.writeValueAsString(p1);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/api/projects/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isAccepted());
    }

    @Test
    public void updateProject_success() throws Exception {
        when(projectService.updateProject(p1, p1.getPid(), 1L)).thenReturn(p1);
        String content = objectWriter.writeValueAsString(p1);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.patch("/api/projects/1/department/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProject_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/projects/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .equals("Project deleted successfully!");

        verify(projectService, times(1)).deleteProject(1);
    }
}
