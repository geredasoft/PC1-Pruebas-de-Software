package com.spring.crud.demo.controller;

import com.spring.crud.demo.model.Student;
import com.spring.crud.demo.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService service;

    @InjectMocks
    private StudentController controller;

    private Student student;

    @BeforeEach
    void setUp() {
        student = Student.builder()
                .rollNo(1)
                .firstName("Binay")
                .lastName("Gurung")
                .marks(300.0f)
                .build();
    }

    @Test
    void sayHello_debeRetornarMensaje() {
        assertEquals("Hello Spring boot", controller.sayHello());
    }

    @Test
    void getAll_debeRetornarEstudiantes() {
        when(service.getAll()).thenReturn(Collections.singletonList(student));

        List<Student> result = controller.getAll();

        assertEquals(1, result.size());
        assertEquals(student, result.get(0));
    }

    @Test
    void getStudentById_debeRetornarEstudiante() {
        when(service.getStudentById(1)).thenReturn(student);

        Student result = controller.getStudentById(1);

        assertEquals(student, result);
        verify(service).getStudentById(1);
    }

    @Test
    void getStudentByName_debeRetornarEstudiantes() {
        when(service.getStudentByFirstName("Binay"))
                .thenReturn(Collections.singletonList(student));

        List<Student> result = controller.getStudentByName("Binay");

        assertEquals(1, result.size());
        assertEquals(student, result.get(0));
    }

    @Test
    void getOneStudentByFirstName_debeRetornarEstudiante() {
        when(service.getOneStudentByFirstName("Binay"))
                .thenReturn(student);

        Student result = controller.getOneStudentByFirstName("Binay");

        assertEquals(student, result);
    }

    @Test
    void getStudentByFirstNameLike_debeRetornarCoincidencias() {
        when(service.getStudentByFirstNameLike("Bin"))
                .thenReturn(Collections.singletonList(student));

        List<Student> result = controller.getStudentByFirstNameLike("Bin");

        assertEquals(1, result.size());
        assertEquals(student, result.get(0));
    }

    @Test
    void getStudentBylName_debeRetornarEstudiante() {
        when(service.getStudentByLastName("Gurung"))
                .thenReturn(student);

        Student result = controller.getStudentBylName("Gurung");

        assertEquals(student, result);
    }

    @Test
    void getStudentBySalaryGreaterThan_debeRetornarEstudiantes() {
        when(service.getStudentBySalaryGreaterThan(200))
                .thenReturn(Collections.singletonList(student));

        List<Student> result =
                controller.getStudentBySalaryGreaterThan(200);

        assertEquals(1, result.size());
    }

    @Test
    void getStudentByCondition_debeRetornarEstudiantes() {
        when(service.getStudentByCondition(student))
                .thenReturn(Collections.singletonList(student));

        List<Student> result =
                controller.getStudentByCondition(student);

        assertEquals(1, result.size());
        assertEquals(student, result.get(0));
    }
}