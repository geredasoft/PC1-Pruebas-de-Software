package com.spring.crud.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.webjars.NotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.crud.demo.model.emp.Employee;
import com.spring.crud.demo.service.EmployeeService;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    private Employee buildEmployee() {
        return Employee.builder()
                .id(1).firstName("Carlos").lastName("Ramos").age(30)
                .noOfChildrens(2).spouse(true).build();
    }

    @Test
    void findAll_DebeRetornarLista() throws Exception {
        doReturn(Arrays.asList(buildEmployee())).when(employeeService).findAll();

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Carlos"));
    }

    @Test
    void findById_CuandoExiste_DebeRetornarEmpleado() throws Exception {
        when(employeeService.findById(1)).thenReturn(buildEmployee());

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Ramos"));
    }

    @Test
    void findById_CuandoNoExiste_DebePropagarExcepcion() {
        when(employeeService.findById(999)).thenThrow(new NotFoundException("no existe"));

        Exception exception = assertThrows(Exception.class, () ->
                mockMvc.perform(get("/employees/999")));

        assertTrue(exception.getCause() instanceof NotFoundException);
    }

    @Test
    void save_DebeCrearEmpleadoYRetornar201() throws Exception {
        Employee empleado = buildEmployee();
        when(employeeService.save(any(Employee.class))).thenReturn(empleado);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empleado)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Carlos"));
    }

    @Test
    void update_CuandoExiste_DebeActualizarYRetornar200() throws Exception {
        Employee actualizado = buildEmployee();
        actualizado.setFirstName("Carlos Actualizado");
        when(employeeService.update(eq(1), any(Employee.class))).thenReturn(actualizado);

        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Carlos Actualizado"));
    }

    @Test
    void update_CuandoNoExiste_DebePropagarExcepcion() {
        Employee cambios = Employee.builder().firstName("Nadie").build();
        when(employeeService.update(eq(999), any(Employee.class)))
                .thenThrow(new NotFoundException("no existe"));

        Exception exception = assertThrows(Exception.class, () ->
                mockMvc.perform(put("/employees/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cambios))));

        assertTrue(exception.getCause() instanceof NotFoundException);
    }

    @Test
    void delete_DebeRetornarMensajeDeExito() throws Exception {
        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted successfully...!"));
    }
}