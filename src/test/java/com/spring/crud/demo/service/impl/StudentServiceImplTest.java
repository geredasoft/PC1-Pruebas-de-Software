package com.spring.crud.demo.service.impl;

import com.spring.crud.demo.model.Student;
import com.spring.crud.demo.repository.StudentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentServiceImpl service;


    @Test
    void debeObtenerTodosLosEstudiantes() {

        // ARRANGE
        Student student1 = Student.builder()
                .rollNo(1)
                .firstName("Juan")
                .lastName("Perez")
                .marks(15f)
                .build();

        Student student2 = Student.builder()
                .rollNo(2)
                .firstName("Maria")
                .lastName("Lopez")
                .marks(18f)
                .build();

        when(repository.findAll()).thenReturn(Arrays.asList(student1, student2));


        // ACT
        List<Student> resultado = service.getAll();


        // ASSERT
        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getFirstName());
        assertEquals("Maria", resultado.get(1).getFirstName());
    }


    @Test
    void debeObtenerEstudiantePorApellido() {

        // ARRANGE
        Student student = Student.builder()
                .rollNo(1)
                .firstName("Juan")
                .lastName("Perez")
                .marks(15f)
                .build();

        when(repository.findAll()).thenReturn(Collections.singletonList(student));


        // ACT
        Student resultado = service.getStudentByLastName("perez");


        // ASSERT
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getFirstName());
        assertEquals("Perez", resultado.getLastName());
    }


    @Test
    void debeRetornarNotFoundCuandoNoExisteApellido() {

        // ARRANGE
        Student student = Student.builder()
                .rollNo(1)
                .firstName("Juan")
                .lastName("Perez")
                .marks(15f)
                .build();

        when(repository.findAll()).thenReturn(Collections.singletonList(student));


        // ACT
        Student resultado = service.getStudentByLastName("Garcia");


        // ASSERT
        assertEquals(0, resultado.getRollNo());
        assertEquals("Not Found", resultado.getFirstName());
    }


    @Test
    void debeObtenerEstudiantePorId() {

        // ARRANGE
        Student student = Student.builder()
                .rollNo(10)
                .firstName("Carlos")
                .lastName("Gomez")
                .marks(17f)
                .build();

        when(repository.findAll()).thenReturn(Collections.singletonList(student));


        // ACT
        Student resultado = service.getStudentById(10);


        // ASSERT
        assertNotNull(resultado);
        assertEquals(10, resultado.getRollNo());
        assertEquals("Carlos", resultado.getFirstName());
    }


    @Test
    void debeRetornarNotFoundCuandoNoExisteId() {

        // ARRANGE
        Student student = Student.builder()
                .rollNo(10)
                .firstName("Carlos")
                .lastName("Gomez")
                .marks(17f)
                .build();

        when(repository.findAll()).thenReturn(Collections.singletonList(student));


        // ACT
        Student resultado = service.getStudentById(20);


        // ASSERT
        assertEquals(0, resultado.getRollNo());
        assertEquals("Not Found", resultado.getFirstName());
    }


    @Test
    void debeObtenerEstudiantesPorNombre() {

        // ARRANGE
        Student student1 = Student.builder()
                .rollNo(1)
                .firstName("Juan")
                .lastName("Perez")
                .marks(15f)
                .build();

        Student student2 = Student.builder()
                .rollNo(2)
                .firstName("Maria")
                .lastName("Lopez")
                .marks(18f)
                .build();

        Student student3 = Student.builder()
                .rollNo(3)
                .firstName("Juan")
                .lastName("Gomez")
                .marks(16f)
                .build();

        when(repository.findAll())
                .thenReturn(Arrays.asList(student1, student2, student3));


        // ACT
        List<Student> resultado = service.getStudentByFirstName("juan");


        // ASSERT
        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getFirstName());
        assertEquals("Juan", resultado.get(1).getFirstName());
    }


    @Test
    void debeObtenerUnEstudiantePorNombre() {

        // ARRANGE
        Student student = Student.builder()
                .rollNo(1)
                .firstName("Pedro")
                .lastName("Ramirez")
                .marks(14f)
                .build();

        when(repository.findByFirstName("Pedro")).thenReturn(student);


        // ACT
        Student resultado = service.getOneStudentByFirstName("Pedro");


        // ASSERT
        assertNotNull(resultado);
        assertEquals("Pedro", resultado.getFirstName());
    }


    @Test
    void debeObtenerEstudiantesPorNombreLike() {

        // ARRANGE
        Student student1 = Student.builder()
                .rollNo(1)
                .firstName("Juan")
                .lastName("Perez")
                .marks(15f)
                .build();

        Student student2 = Student.builder()
                .rollNo(2)
                .firstName("Juana")
                .lastName("Lopez")
                .marks(18f)
                .build();

        when(repository.findByFirstNameLike("Juan"))
                .thenReturn(Arrays.asList(student1, student2));


        // ACT
        List<Student> resultado =
                service.getStudentByFirstNameLike("Juan");


        // ASSERT
        assertEquals(2, resultado.size());
    }


    @Test
    void debeObtenerEstudiantesConNotaMayorAlParametro() {

        // ARRANGE
        Student student1 = Student.builder()
                .rollNo(1)
                .firstName("Juan")
                .lastName("Perez")
                .marks(15f)
                .build();

        Student student2 = Student.builder()
                .rollNo(2)
                .firstName("Maria")
                .lastName("Lopez")
                .marks(18f)
                .build();

        when(repository.findAll())
                .thenReturn(Arrays.asList(student1, student2));


        // ACT
        List<Student> resultado =
                service.getStudentBySalaryGreaterThan(16);


        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("Maria", resultado.get(0).getFirstName());
    }


    @Test
    void debeRetornarListaVaciaCuandoSalaryEsCero() {

        // ARRANGE


        // ACT
        List<Student> resultado =
                service.getStudentBySalaryGreaterThan(0);


        // ASSERT
        assertTrue(resultado.isEmpty());
    }


    @Test
    void debeObtenerEstudiantePorCondicion() {

        // ARRANGE
        Student student = Student.builder()
                .rollNo(1)
                .firstName("Juan")
                .lastName("Perez")
                .marks(15f)
                .build();

        Student condicion = Student.builder()
                .rollNo(1)
                .build();

        when(repository.findAll())
                .thenReturn(Collections.singletonList(student));


        // ACT
        List<Student> resultado =
                service.getStudentByCondition(condicion);


        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getFirstName());
    }


    @Test
    void debeRetornarListaVaciaCuandoCondicionEsNula() {

        // ARRANGE
        when(repository.findAll()).thenReturn(Collections.emptyList());


        // ACT
        List<Student> resultado =
                service.getStudentByCondition(null);


        // ASSERT
        assertTrue(resultado.isEmpty());
    }
}
