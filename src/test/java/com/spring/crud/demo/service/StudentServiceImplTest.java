package com.spring.crud.demo.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.spring.crud.demo.model.Student;
import com.spring.crud.demo.repository.StudentRepository;
import com.spring.crud.demo.service.impl.StudentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student mockStudent;
    private Student mockStudent2;

    @BeforeEach
    void setUp() {
        mockStudent = Student.builder()
                .id(1).rollNo(101).firstName("Jorge").lastName("Candela").marks(95.5f).build();
        mockStudent2 = Student.builder()
                .id(2).rollNo(102).firstName("Luis").lastName("Perez").marks(70.0f).build();
    }

    // ---------- getAll ----------

    @Test
    void getAll_DebeRetornarTodosLosEstudiantes() {
        when(studentRepository.findAll()).thenReturn(Collections.singletonList(mockStudent));
        List<Student> resultado = studentService.getAll();
        assertEquals(1, resultado.size());
    }

    // ---------- getStudentById ----------

    @Test
    void getStudentById_CuandoExiste_DebeRetornarEstudiante() {
        when(studentRepository.findAll()).thenReturn(Collections.singletonList(mockStudent));
        Student resultado = studentService.getStudentById(101);
        assertEquals("Jorge", resultado.getFirstName());
    }

    @Test
    void getStudentById_CuandoNoExiste_DebeRetornarNotFound() {
        when(studentRepository.findAll()).thenReturn(Collections.singletonList(mockStudent));
        Student resultado = studentService.getStudentById(999);
        assertEquals("Not Found", resultado.getFirstName());
    }

    // ---------- getStudentByFirstName ----------

    @Test
    void getStudentByFirstName_DebeRetornarListaFiltrada() {
        when(studentRepository.findAll()).thenReturn(Collections.singletonList(mockStudent));
        List<Student> resultado = studentService.getStudentByFirstName("Jorge");
        assertEquals(1, resultado.size());
    }

    @Test
    void getStudentByFirstName_SinCoincidencias_DebeRetornarListaVacia() {
        when(studentRepository.findAll()).thenReturn(Collections.singletonList(mockStudent));
        List<Student> resultado = studentService.getStudentByFirstName("Zzz");
        assertTrue(resultado.isEmpty());
    }

    // ---------- getOneStudentByFirstName ----------

    @Test
    void getOneStudentByFirstName_DebeRetornarUnSoloEstudiante() {
        when(studentRepository.findByFirstName("Jorge")).thenReturn(mockStudent);
        Student resultado = studentService.getOneStudentByFirstName("Jorge");
        assertEquals("Jorge", resultado.getFirstName());
    }

    @Test
    void getOneStudentByFirstName_CuandoNoExiste_DebeRetornarNull() {
        when(studentRepository.findByFirstName("Zzz")).thenReturn(null);
        Student resultado = studentService.getOneStudentByFirstName("Zzz");
        assertNull(resultado);
    }

    // ---------- getStudentByFirstNameLike ----------

    @Test
    void getStudentByFirstNameLike_DebeRetornarCoincidencias() {
        when(studentRepository.findByFirstNameLike("%Jor%")).thenReturn(Collections.singletonList(mockStudent));
        List<Student> resultado = studentService.getStudentByFirstNameLike("%Jor%");
        assertEquals(1, resultado.size());
    }

    // ---------- getStudentByLastName ----------

    @Test
    void getStudentByLastName_DebeRetornarEstudiante() {
        when(studentRepository.findAll()).thenReturn(Collections.singletonList(mockStudent));
        Student resultado = studentService.getStudentByLastName("Candela");
        assertEquals("Candela", resultado.getLastName());
    }

    @Test
    void getStudentByLastName_CuandoNoExiste_DebeRetornarNotFound() {
        when(studentRepository.findAll()).thenReturn(Collections.singletonList(mockStudent));
        Student resultado = studentService.getStudentByLastName("Inexistente");
        assertEquals("Not Found", resultado.getFirstName());
    }

    // ---------- getStudentBySalaryGreaterThan ----------

    @Test
    void getStudentBySalaryGreaterThan_DebeFiltrarPorNota() {
        Student mockBajo = Student.builder().id(2).rollNo(102).firstName("Luis").marks(50.0f).build();
        when(studentRepository.findAll()).thenReturn(Arrays.asList(mockStudent, mockBajo));

        List<Student> resultado = studentService.getStudentBySalaryGreaterThan(60);
        assertEquals(1, resultado.size());
        assertEquals("Jorge", resultado.get(0).getFirstName());
    }

    @Test
    void getStudentBySalaryGreaterThan_ConValorCero_DebeRetornarListaVacia() {
        List<Student> resultado = studentService.getStudentBySalaryGreaterThan(0);
        assertTrue(resultado.isEmpty());
        verify(studentRepository, never()).findAll();
    }

    // ---------- getStudentByCondition ----------

    @Test
    void getStudentByCondition_DebeRetornarListaFiltrada() {
        when(studentRepository.findAll()).thenReturn(Collections.singletonList(mockStudent));
        List<Student> resultado = studentService.getStudentByCondition(mockStudent);
        assertEquals(1, resultado.size());
    }

    @Test
    void getStudentByCondition_StudentNulo_DebeRetornarListaVacia() {
        List<Student> resultado = studentService.getStudentByCondition(null);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void getStudentByCondition_TodosLosCamposVacios_DebeRetornarListaVacia() {
        Student vacio = Student.builder().build();
        List<Student> resultado = studentService.getStudentByCondition(vacio);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void getStudentByCondition_Completo_CoincideExacto() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(mockStudent, mockStudent2));
        Student filtro = Student.builder()
                .rollNo(101).firstName("Jorge").lastName("Candela").marks(95.5f).build();
        List<Student> resultado = studentService.getStudentByCondition(filtro);
        assertEquals(1, resultado.size());
    }

    @Test
    void getStudentByCondition_CompletoSinCoincidencia_DebeRetornarListaVacia() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(mockStudent, mockStudent2));
        Student filtro = Student.builder()
                .rollNo(999).firstName("Nadie").lastName("Nadie").marks(1f).build();
        List<Student> resultado = studentService.getStudentByCondition(filtro);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void getStudentByCondition_SoloRollNo_DebeRetornarCoincidencia() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(mockStudent, mockStudent2));
        Student filtro = Student.builder().rollNo(101).build();
        List<Student> resultado = studentService.getStudentByCondition(filtro);
        assertEquals(1, resultado.size());
    }

    @Test
    void getStudentByCondition_SoloFirstNameParcial_DebeRetornarCoincidencia() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(mockStudent, mockStudent2));
        Student filtro = Student.builder().firstName("Jor").build();
        List<Student> resultado = studentService.getStudentByCondition(filtro);
        assertEquals(1, resultado.size());
    }

    @Test
    void getStudentByCondition_SoloLastName_DebeRetornarCoincidencia() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(mockStudent, mockStudent2));
        Student filtro = Student.builder().lastName("Perez").build();
        List<Student> resultado = studentService.getStudentByCondition(filtro);
        assertEquals(1, resultado.size());
    }

    @Test
    void getStudentByCondition_SoloMarks_DebeRetornarCoincidencia() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(mockStudent, mockStudent2));
        Student filtro = Student.builder().marks(70.0f).build();
        List<Student> resultado = studentService.getStudentByCondition(filtro);
        assertEquals(1, resultado.size());
    }
}