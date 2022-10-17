package com.assignment.employeemanagementapp;

import com.assignment.employeemanagementapp.exception.ResourceNotFoundException;
import com.assignment.employeemanagementapp.model.Department;
import com.assignment.employeemanagementapp.model.Employee;
import com.assignment.employeemanagementapp.model.Project;
import com.assignment.employeemanagementapp.repositories.DepartmentRepository;
import com.assignment.employeemanagementapp.repositories.EmployeeRepository;
import com.assignment.employeemanagementapp.repositories.ProjectRepository;
import com.assignment.employeemanagementapp.service.impl.EmployeeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    private MockMvc mockMvc;

    Employee e1 = new Employee(1L, "Test1", "Test1", "Test1", null, new HashSet<>());
    Employee e2 = new Employee(2L, "Test2", "Test2", "Test2", null, null);
    Employee e3 = new Employee(3L, "Test3", "Test3", "Test3", null, null);
    Department d = new Department(1L, "TEST", "Test");
    Project p = new Project(1L, "Test", "test", "Test", new HashSet<>());


    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(employeeServiceImpl).build();
    }

    @Test
    public void getAllEmployees_success() throws Exception {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(e1,e2,e3));
        assertThat(employeeServiceImpl.getAllEmployees().size()).isEqualTo(3);
    }

    @Test
    public void getEmployeeById_success() throws Exception {
        when(employeeRepository.findById(e1.getId())).thenReturn(Optional.ofNullable(e1));
        assertThat(employeeServiceImpl.getEmployeeById(e1.getId())).isNotNull();
    }

    @Test
    public void getEmployeesById_throwsException() {
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> employeeServiceImpl.getEmployeeById(2L))
                .withMessage("Employee not found with Id: '2'");
    }

    @Test
    public void getEmployeesByDepartmentId_success() throws Exception {
        when(departmentRepository.findById(d.getId())).thenReturn(Optional.ofNullable(d));
        when(employeeRepository.findByDeptId(d.getId())).thenReturn(Arrays.asList(e1,e2,e3));
        assertThat(employeeServiceImpl.getEmployeeByDeptId(1)).isNotNull();
    }

    @Test
    public void getEmployeesByDepartmentId_throwsException() {
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> employeeServiceImpl.getEmployeeByDeptId(2L))
                .withMessage("Department not found with Id: '2'");
    }

    @Test
    public void getEmployeeByIdAndDepartmentId_success() throws Exception {
        when(departmentRepository.findById(d.getId())).thenReturn(Optional.ofNullable(d));
        when(employeeRepository.findById(e1.getId())).thenReturn(Optional.ofNullable(e1));
        when(employeeRepository.findByIdAndDeptId(e1.getId(), d.getId())).thenReturn(e1);
        assertThat(employeeServiceImpl.getEmployeeByIdAndDeptId(e1.getId(), d.getId())).isNotNull();
    }

    @Test
    public void getEmployeeByIdAndDepartmentId_throwsException() {
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> employeeServiceImpl.getEmployeeByIdAndDeptId(2L, 1L))
                .withMessage("Department not found with Id: '1'");
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.ofNullable(d));
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> employeeServiceImpl.getEmployeeByIdAndDeptId(1L, 1L))
                .withMessage("Employee not found with Id: '1'");
    }

    @Test
    public void createEmployee_success() throws Exception {
        when(employeeRepository.save(e1)).thenReturn(e1);
        when(departmentRepository.findById(d.getId())).thenReturn(Optional.ofNullable(d));
        assertThat(employeeServiceImpl.saveEmployee(e1, d.getId())).isNotNull();
    }

    @Test
    public void createEmployee_throwsException() {
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> employeeServiceImpl.saveEmployee(e1, 1L))
                .withMessage("Department not found with Id: '1'");
    }

    @Test
    public void assignProjectsToEmployee_success() throws Exception {
        when(employeeRepository.findById(e1.getId())).thenReturn(Optional.ofNullable(e1));
        when(projectRepository.findById(p.getPid())).thenReturn(Optional.ofNullable(p));
        when(employeeRepository.save(e1)).thenReturn(e1);
        assertThat(employeeServiceImpl.assignProjectsToEmployee(e1.getId(), p.getPid())).isNotNull();
    }

    @Test
    public void assignProjectsToEmployee_throwsException() {
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> employeeServiceImpl.assignProjectsToEmployee(2L, 1L))
                .withMessage("Employee not found with Id: '2'");
        Mockito.when(employeeRepository.findById(e1.getId())).thenReturn(Optional.ofNullable(e1));
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> employeeServiceImpl.assignProjectsToEmployee(1L, 1L))
                .withMessage("Project not found with Id: '1'");
    }

    @Test
    public void updateEmployee_success() throws Exception {
        when(employeeRepository.save(e1)).thenReturn(e1);
        when(employeeRepository.findById(e1.getId())).thenReturn(Optional.ofNullable(e1));
        when(departmentRepository.findById(d.getId())).thenReturn(Optional.ofNullable(d));
        assertThat(employeeServiceImpl.updateEmployee(e1, e1.getId(), 1)).isNotNull();
    }

    @Test
    public void updateEmployee_throwsException() {
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> employeeServiceImpl.updateEmployee(e1, 2L, 1L))
                .withMessage("Employee not found with Id: '2'");
        Mockito.when(employeeRepository.findById(e1.getId())).thenReturn(Optional.ofNullable(e1));
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> employeeServiceImpl.updateEmployee(e1, 1L, 1L))
                .withMessage("Department not found with Id: '1'");
    }

    @Test
    public void deleteEmployee_success() throws Exception {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(e1));
        employeeServiceImpl.deleteEmployee(1L);
        verify(employeeRepository).findById(1L);
    }

    @Test
    public void deleteEmployee_throwsException() {
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> employeeServiceImpl.deleteEmployee(1L))
                .withMessage("Employee not found with Id: '1'");
    }
}
