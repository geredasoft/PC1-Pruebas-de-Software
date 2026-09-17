package com.spring.crud.demo.controller;

import com.spring.crud.demo.model.Student;
import com.spring.crud.demo.service.StudentService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController controller;


    @Test
    void debeRetornarMensajeHelloSpringBoot() {
        // ARRANGE
        String mensajeEsperado = "Hello Spring boot";

        // ACT
        String resultado = controller.sayHello();

        // ASSERT
        assertEquals(mensajeEsperado, resultado);
    }


    @Test
    void debeObtenerTodosLosEstudiantes() {
        // ARRANGE
        Student student1 = new Student();
        student1.setRollNo(1);
        student1.setFirstName("Juan");

        Student student2 = new Student();
        student2.setRollNo(2);
        student2.setFirstName("Pedro");

        List<Student> estudiantes = Arrays.asList(student1, student2);

        when(studentService.getAll())
                .thenReturn(estudiantes);

        // ACT
        List<Student> resultado = controller.getAll();

        // ASSERT
        assertEquals(2, resultado.size());
        assertEquals(estudiantes, resultado);

        verify(studentService).getAll();
    }


    @Test
    void debeObtenerEstudiantePorId() {
        // ARRANGE
        Student student = new Student();
        student.setRollNo(1);
        student.setFirstName("Juan");

        when(studentService.getStudentById(1))
                .thenReturn(student);

        // ACT
        Student resultado = controller.getStudentById(1);

        // ASSERT
        assertEquals(student, resultado);
        assertEquals(1, resultado.getRollNo());

        verify(studentService).getStudentById(1);
    }


    @Test
    void debeObtenerEstudiantesPorNombre() {
        // ARRANGE
        Student student1 = new Student();
        student1.setRollNo(1);
        student1.setFirstName("Juan");

        Student student2 = new Student();
        student2.setRollNo(2);
        student2.setFirstName("Juan");

        List<Student> estudiantes = Arrays.asList(student1, student2);

        when(studentService.getStudentByFirstName("Juan"))
                .thenReturn(estudiantes);

        // ACT
        List<Student> resultado = controller.getStudentByName("Juan");

        // ASSERT
        assertEquals(2, resultado.size());
        assertEquals(estudiantes, resultado);

        verify(studentService).getStudentByFirstName("Juan");
    }


    @Test
    void debeObtenerUnEstudiantePorNombre() {
        // ARRANGE
        Student student = new Student();
        student.setRollNo(1);
        student.setFirstName("Juan");

        when(studentService.getOneStudentByFirstName("Juan"))
                .thenReturn(student);

        // ACT
        Student resultado = controller.getOneStudentByFirstName("Juan");

        // ASSERT
        assertEquals(student, resultado);
        assertEquals("Juan", resultado.getFirstName());

        verify(studentService).getOneStudentByFirstName("Juan");
    }


    @Test
    void debeObtenerEstudiantesPorNombreLike() {
        // ARRANGE
        Student student1 = new Student();
        student1.setRollNo(1);
        student1.setFirstName("Juan");

        Student student2 = new Student();
        student2.setRollNo(2);
        student2.setFirstName("Juana");

        List<Student> estudiantes = Arrays.asList(student1, student2);

        when(studentService.getStudentByFirstNameLike("Juan"))
                .thenReturn(estudiantes);

        // ACT
        List<Student> resultado =
                controller.getStudentByFirstNameLike("Juan");

        // ASSERT
        assertEquals(2, resultado.size());
        assertEquals(estudiantes, resultado);

        verify(studentService).getStudentByFirstNameLike("Juan");
    }


    @Test
    void debeObtenerEstudiantePorApellido() {
        // ARRANGE
        Student student = new Student();
        student.setRollNo(1);
        student.setFirstName("Juan");
        student.setLastName("Perez");

        when(studentService.getStudentByLastName("Perez"))
                .thenReturn(student);

        // ACT
        Student resultado = controller.getStudentBylName("Perez");

        // ASSERT
        assertEquals(student, resultado);
        assertEquals("Perez", resultado.getLastName());

        verify(studentService).getStudentByLastName("Perez");
    }


    @Test
    void debeObtenerEstudiantesConSalarioMayor() {
        // ARRANGE
        Student student1 = new Student();
        student1.setRollNo(1);
        student1.setMarks(80);

        Student student2 = new Student();
        student2.setRollNo(2);
        student2.setMarks(90);

        List<Student> estudiantes = Arrays.asList(student1, student2);

        when(studentService.getStudentBySalaryGreaterThan(70))
                .thenReturn(estudiantes);

        // ACT
        List<Student> resultado =
                controller.getStudentBySalaryGreaterThan(70);

        // ASSERT
        assertEquals(2, resultado.size());
        assertEquals(estudiantes, resultado);

        verify(studentService).getStudentBySalaryGreaterThan(70);
    }


    @Test
    void debeObtenerEstudiantesPorCondicion() {
        // ARRANGE
        Student filtro = new Student();
        filtro.setFirstName("Juan");

        Student student = new Student();
        student.setRollNo(1);
        student.setFirstName("Juan");
        student.setLastName("Perez");
        student.setMarks(80);

        List<Student> estudiantes = Arrays.asList(student);

        when(studentService.getStudentByCondition(filtro))
                .thenReturn(estudiantes);

        // ACT
        List<Student> resultado =
                controller.getStudentByCondition(filtro);

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals(student, resultado.get(0));

        verify(studentService).getStudentByCondition(filtro);
    }
}