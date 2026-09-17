package com.spring.crud.demo.service.impl;

import com.spring.crud.demo.model.emp.Employee;
import com.spring.crud.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeServiceImpl service;




    @Test
    void deberiaObtenerTodosLosEmpleados() {

        // Arrange
        Employee empleado1 = new Employee();
        Employee empleado2 = new Employee();

        when(repository.findAll())
                .thenReturn(Arrays.asList(empleado1, empleado2));

        // Act
        List<Employee> resultado = service.findAll();

        // Assert
        assertEquals(2, resultado.size());
    }


    @Test
    void deberiaEncontrarEmpleadoPorId() {

        // Arrange
        Employee empleado = new Employee();

        when(repository.findById(1))
                .thenReturn(Optional.of(empleado));

        // Act
        Employee resultado = service.findById(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(empleado, resultado);
    }




    @Test
    void deberiaLanzarExcepcionCuandoEmpleadoNoExiste() {

        // Arrange
        when(repository.findById(99))
                .thenReturn(Optional.empty());

        // Act
        // Assert
        assertThrows(
                NotFoundException.class,
                () -> service.findById(99)
        );
    }




    @Test
    void deberiaGuardarEmpleado() {

        // Arrange
        Employee empleado = new Employee();

        when(repository.save(empleado))
                .thenReturn(empleado);

        // Act
        Employee resultado = service.save(empleado);

        // Assert
        assertNotNull(resultado);
        assertEquals(empleado, resultado);
    }



    @Test
    void deberiaActualizarEmpleado() {

        // Arrange
        Employee empleado = new Employee();

        when(repository.findById(1))
                .thenReturn(Optional.of(new Employee()));

        when(repository.save(empleado))
                .thenReturn(empleado);

        // Act
        Employee resultado = service.update(1, empleado);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(empleado, resultado);
    }



    @Test
    void deberiaLanzarExcepcionAlActualizarEmpleadoInexistente() {

        // Arrange
        Employee empleado = new Employee();

        when(repository.findById(99))
                .thenReturn(Optional.empty());

        // Act
        // Assert
        assertThrows(
                NotFoundException.class,
                () -> service.update(99, empleado)
        );

        verify(repository, never()).save(empleado);
    }


    @Test
    void deberiaEliminarEmpleado() {

        // Arrange
        Employee empleado = new Employee();

        when(repository.findById(1))
                .thenReturn(Optional.of(empleado));

        // Act
        service.delete(1);

        // Assert
        verify(repository).delete(empleado);
    }




    @Test
    void noDeberiaEliminarCuandoEmpleadoNoExiste() {

        // Arrange
        when(repository.findById(99))
                .thenReturn(Optional.empty());

        // Act
        service.delete(99);

        // Assert
        verify(repository, never()).delete(any(Employee.class));
    }
}