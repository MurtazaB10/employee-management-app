package com.assignment.employeemanagementapp;

import com.assignment.employeemanagementapp.controller.DepartmentController;
import com.assignment.employeemanagementapp.model.Department;
import com.assignment.employeemanagementapp.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

public class DepartmentControllerTest {

    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    Department d1 = new Department(1L, "Test1", "Test1");
    Department d2 = new Department(2L, "Test2", "Test2");
    Department d3 = new Department(3L, "Test3", "Test3");

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
    }

    @Test
    public void getAllDepartments_success() throws Exception {
        List<Department> departments = new ArrayList<>(Arrays.asList(d1,d2,d3));
        when(departmentService.getAllDepartments()).thenReturn(departments);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].dname", is("Test3")))
                .andExpect(jsonPath("$[1].dname", is("Test2")));
    }

    @Test
    public void getDepartmentById_success() throws Exception {
        when(departmentService.getDepartmentById(d1.getId())).thenReturn(d1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/departments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.dname", is("Test1")));
    }

    @Test
    public void saveDepartment_success() throws Exception {
        when(departmentService.saveDepartment(d1)).thenReturn(d1);
        String content = objectMapper.writeValueAsString(d1);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/api/departments")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isCreated());
    }

    @Test
    public void updateDepartment_success() throws Exception {
        when(departmentService.updateDepartment(d1, d1.getId())).thenReturn(d1);
        String content = objectWriter.writeValueAsString(d1);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.put("/api/departments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    public void deleteDepartment_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/departments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .equals("Department deleted successfully!");

        verify(departmentService, times(1)).deleteDepartment(1);
    }
}
