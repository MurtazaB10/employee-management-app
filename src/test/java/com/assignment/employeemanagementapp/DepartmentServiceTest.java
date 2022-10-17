package com.assignment.employeemanagementapp;

import com.assignment.employeemanagementapp.exception.ResourceNotFoundException;
import com.assignment.employeemanagementapp.model.Department;
import com.assignment.employeemanagementapp.repositories.DepartmentRepository;
import com.assignment.employeemanagementapp.service.impl.DepartmentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DepartmentServiceTest {

    private MockMvc mockMvc;

    Department d1 = new Department(1l, "Test1", "Test1");
    Department d2 = new Department(2l, "Test2", "Test2");
    Department d3 = new Department(3l, "Test3", "Test3");
    Department d = new Department(4L, "Test4", "Test4");


    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(departmentService).build();
    }

    @Test
    public void getAllDepartments_success() throws Exception {
        Mockito.when(departmentRepository.findAll()).thenReturn(Arrays.asList(d1,d2,d3));
        assertThat(departmentService.getAllDepartments().size()).isEqualTo(3);
    }

    @Test
    public void getDepartmentById_success() throws Exception {
        long id = 1L;
        Mockito.when(departmentRepository.findById(id)).thenReturn(Optional.ofNullable(d1));
        assertThat(departmentService.getDepartmentById(id)).isNotNull();
    }

    @Test
    public void getDepartmentById_throwsException(){
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> departmentService.getDepartmentById(1L))
                .withMessage("Department not found with Id: '1'");
    }

    @Test
    public void createDepartment_success() throws Exception {
        Mockito.when(departmentRepository.save(d)).thenReturn(d);
        assertThat(departmentService.saveDepartment(d)).isNotNull();
    }

    @Test
    public void updateDepartment_success() throws Exception {
        Mockito.when(departmentRepository.save(d)).thenReturn(d);
        Mockito.when(departmentRepository.findById(d.getId())).thenReturn(Optional.ofNullable(d));
        assertThat(departmentService.updateDepartment(d, d.getId())).isNotNull();
    }

    @Test
    public void updateDepartment_throwsException(){
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> departmentService.getDepartmentById(1L))
                .withMessage("Department not found with Id: '1'");
    }

    @Test
    public void deleteDepartment_success() throws Exception {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(d1));
        departmentService.deleteDepartment(1L);
        verify(departmentRepository).findById(1L);
    }

    @Test
    public void deleteDepartment_throwsException(){
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> departmentService.getDepartmentById(1L))
                .withMessage("Department not found with Id: '1'");
    }
}
