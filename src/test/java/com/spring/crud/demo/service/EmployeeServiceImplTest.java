package com.spring.crud.demo.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;

import com.spring.crud.demo.model.emp.Address;
import com.spring.crud.demo.model.emp.Employee;
import com.spring.crud.demo.model.emp.PhoneNumber;
import com.spring.crud.demo.repository.EmployeeRepository;
import com.spring.crud.demo.service.impl.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee mockEmployee;

    private Employee buildEmployee() {
        Address address = Address.builder()
                .id(1).streetAddress("Av. Principal").city("Lima")
                .state("Lima").country("Peru").postalCode("15001").build();

        PhoneNumber phone = PhoneNumber.builder()
                .id(1).type("mobile").number("999999999").build();

        return Employee.builder()
                .id(1).firstName("Carlos").lastName("Ramos").age(30)
                .noOfChildrens(2).spouse(true)
                .address(address)
                .phoneNumbers(Arrays.asList(phone))
                .hobbies(Arrays.asList("Fútbol", "Lectura"))
                .build();
    }

    @BeforeEach
    void setUp() {
        mockEmployee = buildEmployee();
    }

    @Test
    void findAll_DebeRetornarListaDeEmpleados() {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(mockEmployee));
        List<Employee> resultado = employeeService.findAll();
        assertEquals(1, resultado.size());
    }

    @Test
    void findById_CuandoExiste_DebeRetornarEmpleadoConSusRelaciones() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(mockEmployee));
        Employee resultado = employeeService.findById(1);
        assertEquals("Carlos", resultado.getFirstName());
        assertEquals("Lima", resultado.getAddress().getCity());
        assertEquals(1, resultado.getPhoneNumbers().size());
        assertEquals(2, resultado.getHobbies().size());
    }

    @Test
    void findById_CuandoNoExiste_DebeLanzarExcepcion() {
        when(employeeRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> employeeService.findById(999));
    }

    @Test
    void save_DebeGuardarYRetornarEmpleado() {
        when(employeeRepository.save(mockEmployee)).thenReturn(mockEmployee);
        Employee resultado = employeeService.save(mockEmployee);
        assertEquals("Carlos", resultado.getFirstName());
        verify(employeeRepository, times(1)).save(mockEmployee);
    }

    @Test
    void update_CuandoExiste_DebeActualizarYRetornarEmpleado() {
        Employee cambios = buildEmployee();
        cambios.setFirstName("Carlos Actualizado");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(mockEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(cambios);

        Employee resultado = employeeService.update(1, cambios);

        assertEquals(1, cambios.getId());
        assertEquals("Carlos Actualizado", resultado.getFirstName());
    }

    @Test
    void update_CuandoNoExiste_DebeLanzarExcepcionYNoGuardar() {
        Employee cambios = Employee.builder().firstName("Nadie").build();
        when(employeeRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> employeeService.update(999, cambios));
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void delete_CuandoExiste_DebeEliminarEmpleado() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(mockEmployee));
        employeeService.delete(1);
        verify(employeeRepository, times(1)).delete(mockEmployee);
    }

    @Test
    void delete_CuandoNoExiste_NoDebeEliminarNada() {
        when(employeeRepository.findById(999)).thenReturn(Optional.empty());
        employeeService.delete(999);
        verify(employeeRepository, never()).delete(any(Employee.class));
    }
}