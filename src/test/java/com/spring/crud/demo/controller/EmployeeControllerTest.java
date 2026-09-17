package com.spring.crud.demo.controller;

import com.spring.crud.demo.model.emp.Employee;
import com.spring.crud.demo.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService service;

    @InjectMocks
    private EmployeeController controller;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1)
                .firstName("Rahul")
                .lastName("Ghadage")
                .build();
    }

    @AfterEach
    void limpiarContextoServlet() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void findAll_debeRetornar200() {
        when(service.findAll()).thenReturn(Collections.singletonList(employee));

        ResponseEntity<?> response = controller.findAll();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        verify(service).findAll();
    }

    @Test
    void findById_debeRetornar200() {
        when(service.findById(1)).thenReturn(employee);

        ResponseEntity<?> response = controller.findById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(employee, response.getBody());

        verify(service).findById(1);
    }

    @Test
    void save_debeRetornar201() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("");

        RequestContextHolder.setRequestAttributes(
                new ServletRequestAttributes(request)
        );

        when(service.save(employee)).thenReturn(employee);

        ResponseEntity<?> response = controller.save(employee);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(employee, response.getBody());

        verify(service).save(employee);
    }

    @Test
    void update_debeRetornar200() {
        when(service.update(1, employee)).thenReturn(employee);

        ResponseEntity<?> response = controller.update(1, employee);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(employee, response.getBody());

        verify(service).update(1, employee);
    }

    @Test
    void delete_debeRetornar200() {
        doNothing().when(service).delete(1);

        ResponseEntity<?> response = controller.delete(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Deleted successfully...!", response.getBody());

        verify(service).delete(1);
    }
}