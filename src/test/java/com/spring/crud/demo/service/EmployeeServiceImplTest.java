package com.spring.crud.demo.service;

import com.spring.crud.demo.model.emp.Employee;
import com.spring.crud.demo.repository.EmployeeRepository;
import com.spring.crud.demo.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;

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

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1)
                .firstName("Rahul")
                .lastName("Ghadage")
                .age(28)
                .build();
    }

    @Test
    void findAll_debeRetornarTodosLosEmpleados() {
        List<Employee> employees = Collections.singletonList(employee);

        when(repository.findAll()).thenReturn(employees);

        List<Employee> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals(employee, result.get(0));

        verify(repository).findAll();
    }

    @Test
    void findAll_debeRetornarListaVaciaCuandoNoHayEmpleados() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Employee> result = service.findAll();

        assertTrue(result.isEmpty());

        verify(repository).findAll();
    }

    @Test
    void findById_debeRetornarEmpleadoExistente() {
        when(repository.findById(1)).thenReturn(Optional.of(employee));

        Employee result = service.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Rahul", result.getFirstName());

        verify(repository).findById(1);
    }

    @Test
    void findById_debeLanzarExcepcionCuandoNoExiste() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.findById(99)
        );

        verify(repository).findById(99);
    }

    @Test
    void save_debeGuardarEmpleado() {
        when(repository.save(employee)).thenReturn(employee);

        Employee result = service.save(employee);

        assertNotNull(result);
        assertEquals(employee, result);

        verify(repository).save(employee);
    }

    @Test
    void update_debeActualizarEmpleadoExistente() {
        Employee updatedEmployee = Employee.builder()
                .firstName("Carlos")
                .lastName("Perez")
                .age(30)
                .build();

        when(repository.findById(1)).thenReturn(Optional.of(employee));
        when(repository.save(updatedEmployee)).thenReturn(updatedEmployee);

        Employee result = service.update(1, updatedEmployee);

        assertEquals(1, updatedEmployee.getId());
        assertEquals(updatedEmployee, result);

        verify(repository).findById(1);
        verify(repository).save(updatedEmployee);
    }

    @Test
    void update_debeLanzarExcepcionCuandoEmpleadoNoExiste() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        Employee updatedEmployee = Employee.builder()
                .firstName("Carlos")
                .build();

        assertThrows(
                NotFoundException.class,
                () -> service.update(99, updatedEmployee)
        );

        verify(repository).findById(99);
        verify(repository, never()).save(any(Employee.class));
    }

    @Test
    void delete_debeEliminarEmpleadoExistente() {
        when(repository.findById(1)).thenReturn(Optional.of(employee));

        service.delete(1);

        verify(repository).findById(1);
        verify(repository).delete(employee);
    }

    @Test
    void delete_noDebeEliminarCuandoEmpleadoNoExiste() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        service.delete(99);

        verify(repository).findById(99);
        verify(repository, never()).delete(any(Employee.class));
    }
}