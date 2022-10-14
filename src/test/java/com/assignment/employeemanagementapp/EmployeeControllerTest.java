package com.assignment.employeemanagementapp;
import com.assignment.employeemanagementapp.controller.EmployeeController;
import com.assignment.employeemanagementapp.model.Department;
import com.assignment.employeemanagementapp.model.Employee;
import com.assignment.employeemanagementapp.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    Employee e1 = new Employee(1L, "first","last","email", null, null);
    Employee e2 = new Employee(2L, "Test","Test","Test",null,null);
    Employee e3 = new Employee(3L, "3","3","3",null,null);

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void getAllEmployees_success() throws Exception {
        List<Employee> employees = new ArrayList<>(Arrays.asList(e1,e2,e3));
        Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].firstName", is("3")))
                .andExpect(jsonPath("$[1].firstName", is("Test")));
    }

    @Test
    public void getEmployeesById_success() throws Exception {
        Mockito.when(employeeService.getEmployeeById(e1.getId())).thenReturn(e1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", notNullValue()))
                        .andExpect(jsonPath("$.firstName", is("first")));
    }

    @Test
    public void createEmployee_success() throws Exception {
        Employee record = new Employee(1L, "Testing", "Testing", "Testing", null , null);
        Mockito.when(employeeService.saveEmployee(record, 3)).thenReturn(record);
        String content = objectMapper.writeValueAsString(record);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post("/api/employees/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        String res = mockMvc.perform(mockHttpServletRequestBuilder)
                .andReturn().getRequest().getContentAsString();
        System.out.println("*******");
        System.out.println(res);
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$", notNullValue()))
//                .andExpect(jsonPath("$.firstName", is("Testing")));

    }

    @Test
    public void updateEmployee_success() throws Exception {
        Employee update = new Employee(1L, "Testing", "Testing","Testing",null,null);
        Mockito.when(employeeService.getEmployeeById(e1.getId())).thenReturn(e1);
        Mockito.when(employeeService.updateEmployee(update, update.getId(), 3)).thenReturn(update);
        String content = objectWriter.writeValueAsString(update);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.put("/api/employees/1/department/3")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firatName", is("Testing")));
    }
}
