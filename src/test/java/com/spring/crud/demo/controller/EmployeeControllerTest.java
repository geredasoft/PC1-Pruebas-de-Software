package com.spring.crud.demo.controller;

import com.spring.crud.demo.model.emp.Employee;
import com.spring.crud.demo.service.EmployeeService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService service;

    @InjectMocks
    private EmployeeController controller;


    @Test
    void debeObtenerTodosLosEmpleados() {
        // ARRANGE
        Employee employee1 = new Employee();
        employee1.setId(1);

        Employee employee2 = new Employee();
        employee2.setId(2);

        List<Employee> empleados =
                Arrays.asList(employee1, employee2);

        when(service.findAll())
                .thenReturn(empleados);

        // ACT
        ResponseEntity<List<?>> response =
                controller.findAll();

        // ASSERT
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(empleados, response.getBody());

        verify(service).findAll();
    }


    @Test
    void debeObtenerEmpleadoPorId() {
        // ARRANGE
        Employee employee = new Employee();
        employee.setId(1);

        when(service.findById(1))
                .thenReturn(employee);

        // ACT
        ResponseEntity<?> response =
                controller.findById(1);

        // ASSERT
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());

        verify(service).findById(1);
    }


    @Test
    void debeGuardarEmpleado() {
        // ARRANGE
        MockHttpServletRequest request =
                new MockHttpServletRequest();

        request.setContextPath("/employees");

        RequestContextHolder.setRequestAttributes(
                new ServletRequestAttributes(request)
        );

        Employee employee = new Employee();
        employee.setId(1);

        when(service.save(employee))
                .thenReturn(employee);

        // ACT
        ResponseEntity<?> response =
                controller.save(employee);

        // ASSERT
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employee, response.getBody());
        assertNotNull(response.getHeaders().getLocation());

        verify(service).save(employee);

        // LIMPIAR
        RequestContextHolder.resetRequestAttributes();
    }


    @Test
    void debeActualizarEmpleado() {
        // ARRANGE
        Employee employee = new Employee();
        employee.setId(1);

        when(service.update(1, employee))
                .thenReturn(employee);

        // ACT
        ResponseEntity<?> response =
                controller.update(1, employee);

        // ASSERT
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());

        verify(service).update(1, employee);
    }


    @Test
    void debeEliminarEmpleado() {
        // ARRANGE
        doNothing().when(service).delete(1);

        // ACT
        ResponseEntity<?> response =
                controller.delete(1);

        // ASSERT
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(
                "Deleted successfully...!",
                response.getBody()
        );

        verify(service).delete(1);
    }
}