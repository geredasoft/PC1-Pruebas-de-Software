package com.spring.crud.demo.service;

import com.spring.crud.demo.model.Student;
import com.spring.crud.demo.repository.StudentRepository;
import com.spring.crud.demo.service.impl.StudentServiceImpl;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentServiceImpl service;

    private Student binay;
    private Student rahul;
    private Student salman;
    private Student aamir;

    @BeforeEach
    void setUp() {
        binay = Student.builder()
                .rollNo(1)
                .firstName("Binay")
                .lastName("Gurung")
                .marks(300.0f)
                .build();

        rahul = Student.builder()
                .rollNo(2)
                .firstName("Rahul")
                .lastName("Ghadage")
                .marks(950.0f)
                .build();

        salman = Student.builder()
                .rollNo(4)
                .firstName("Salman")
                .lastName("Khan")
                .marks(600.0f)
                .build();

        aamir = Student.builder()
                .rollNo(5)
                .firstName("Aamir")
                .lastName("Khan")
                .marks(700.0f)
                .build();
    }

    private List<Student> students() {
        return Arrays.asList(binay, rahul, salman, aamir);
    }

    @Test
    void getAll_debeRetornarTodosLosEstudiantes() {
        when(repository.findAll()).thenReturn(students());

        List<Student> result = service.getAll();

        assertEquals(4, result.size());
        verify(repository).findAll();
    }

    @Test
    void getAll_debeRetornarListaVacia() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Student> result = service.getAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void getStudentByLastName_debeEncontrarIgnorandoMayusculas() {
        when(repository.findAll()).thenReturn(students());

        Student result = service.getStudentByLastName("kHaN");

        assertNotNull(result);
        assertEquals("Salman", result.getFirstName());
        assertEquals("Khan", result.getLastName());
    }

    @Test
    void getStudentByLastName_debeRetornarNotFoundSiNoExiste() {
        when(repository.findAll()).thenReturn(students());

        Student result = service.getStudentByLastName("Perez");

        assertEquals(0, result.getRollNo());
        assertEquals("Not Found", result.getFirstName());
        assertEquals("Please enter valid id", result.getLastName());
        assertEquals(0f, result.getMarks());
    }

    @Test
    void getStudentById_debeEncontrarEstudiante() {
        when(repository.findAll()).thenReturn(students());

        Student result = service.getStudentById(2);

        assertEquals("Rahul", result.getFirstName());
        assertEquals(950.0f, result.getMarks());
    }

    @Test
    void getStudentById_debeRetornarNotFoundSiNoExiste() {
        when(repository.findAll()).thenReturn(students());

        Student result = service.getStudentById(99);

        assertEquals(0, result.getRollNo());
        assertEquals("Not Found", result.getFirstName());
    }

    @Test
    void getStudentByFirstName_debeEncontrarIgnorandoMayusculas() {
        when(repository.findAll()).thenReturn(students());

        List<Student> result = service.getStudentByFirstName("RAHUL");

        assertEquals(1, result.size());
        assertEquals("Rahul", result.get(0).getFirstName());
    }

    @Test
    void getStudentByFirstName_debeRetornarListaVaciaSiNoExiste() {
        when(repository.findAll()).thenReturn(students());

        List<Student> result = service.getStudentByFirstName("Carlos");

        assertTrue(result.isEmpty());
    }

    @Test
    void getOneStudentByFirstName_debeUsarRepositorio() {
        when(repository.findByFirstName("Rahul")).thenReturn(rahul);

        Student result = service.getOneStudentByFirstName("Rahul");

        assertEquals(rahul, result);
        verify(repository).findByFirstName("Rahul");
    }

    @Test
    void getStudentByFirstNameLike_debeUsarRepositorio() {
        when(repository.findByFirstNameLike("Rah")).thenReturn(
                Collections.singletonList(rahul)
        );

        List<Student> result = service.getStudentByFirstNameLike("Rah");

        assertEquals(1, result.size());
        assertEquals("Rahul", result.get(0).getFirstName());

        verify(repository).findByFirstNameLike("Rah");
    }

    @Test
    void getStudentBySalaryGreaterThan_debeEncontrarNotasMayores() {
        when(repository.findAll()).thenReturn(students());

        List<Student> result = service.getStudentBySalaryGreaterThan(650);

        assertEquals(2, result.size());
        assertTrue(result.contains(rahul));
        assertTrue(result.contains(aamir));
    }

    @Test
    void getStudentBySalaryGreaterThan_conValorCero_debeRetornarVacio() {
        List<Student> result = service.getStudentBySalaryGreaterThan(0);

        assertTrue(result.isEmpty());

        verify(repository, never()).findAll();
    }

    @Test
    void getStudentBySalaryGreaterThan_conValorNegativo_debeRetornarVacio() {
        List<Student> result = service.getStudentBySalaryGreaterThan(-10);

        assertTrue(result.isEmpty());

        verify(repository, never()).findAll();
    }

    @Test
    void getStudentByCondition_conStudentNull_debeRetornarVacio() {
        when(repository.findAll()).thenReturn(students());

        List<Student> result = service.getStudentByCondition(null);

        assertTrue(result.isEmpty());
    }

    @Test
    void getStudentByCondition_conObjetoVacio_debeRetornarVacio() {
        when(repository.findAll()).thenReturn(students());

        Student condition = Student.builder().build();

        List<Student> result = service.getStudentByCondition(condition);

        assertTrue(result.isEmpty());
    }

    @Test
    void getStudentByCondition_porRollNo_debeEncontrarEstudiante() {
        when(repository.findAll()).thenReturn(students());

        Student condition = Student.builder()
                .rollNo(2)
                .build();

        List<Student> result = service.getStudentByCondition(condition);

        assertEquals(1, result.size());
        assertEquals("Rahul", result.get(0).getFirstName());
    }

    @Test
    void getStudentByCondition_porFirstName_debeEncontrarCoincidencias() {
        when(repository.findAll()).thenReturn(students());

        Student condition = Student.builder()
                .firstName("rah")
                .build();

        List<Student> result = service.getStudentByCondition(condition);

        assertEquals(1, result.size());
        assertEquals("Rahul", result.get(0).getFirstName());
    }

    @Test
    void getStudentByCondition_porLastName_debeEncontrarCoincidencia() {
        when(repository.findAll()).thenReturn(students());

        Student condition = Student.builder()
                .lastName("Khan")
                .build();

        List<Student> result = service.getStudentByCondition(condition);

        assertEquals(2, result.size());
        assertTrue(result.contains(salman));
        assertTrue(result.contains(aamir));
    }

    @Test
    void getStudentByCondition_porMarks_debeEncontrarCoincidencia() {
        when(repository.findAll()).thenReturn(students());

        Student condition = Student.builder()
                .marks(700.0f)
                .build();

        List<Student> result = service.getStudentByCondition(condition);

        assertEquals(1, result.size());
        assertEquals("Aamir", result.get(0).getFirstName());
    }

    @Test
    void getStudentByCondition_conTodosLosCampos_debeEncontrarCoincidenciaExacta() {
        when(repository.findAll()).thenReturn(students());

        Student condition = Student.builder()
                .rollNo(2)
                .firstName("Rahul")
                .lastName("Ghadage")
                .marks(950.0f)
                .build();

        List<Student> result = service.getStudentByCondition(condition);

        assertEquals(1, result.size());
        assertEquals(rahul, result.get(0));
    }

    @Test
    void getStudentByCondition_conTodosLosCamposIncorrectos_debeRetornarVacio() {
        when(repository.findAll()).thenReturn(students());

        Student condition = Student.builder()
                .rollNo(2)
                .firstName("Rahul")
                .lastName("Incorrecto")
                .marks(950.0f)
                .build();

        List<Student> result = service.getStudentByCondition(condition);

        assertTrue(result.isEmpty());
    }
}