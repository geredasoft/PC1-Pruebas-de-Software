package com.spring.crud.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.spring.crud.demo.model.Student;
import com.spring.crud.demo.service.StudentService;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private StudentService studentService;

        @Test
        void sayHello_DebeRetornarString() throws Exception {
                mockMvc.perform(get("/student-jpa/say"))
                                .andExpect(status().isOk())
                                .andExpect(content().string("Hello Spring boot"));
        }

        @Test
        void getAll_DebeRetornarLista() throws Exception {
                Student mockStudent = Student.builder().rollNo(101).firstName("Jorge").build();
                when(studentService.getAll()).thenReturn(Collections.singletonList(mockStudent));

                mockMvc.perform(get("/student-jpa"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].firstName").value("Jorge"));
        }

        @Test
        void getStudentById_DebeRetornarEstudiante() throws Exception {
                Student mockStudent = Student.builder().rollNo(101).firstName("Jorge").build();
                when(studentService.getStudentById(101)).thenReturn(mockStudent);

                mockMvc.perform(get("/student-jpa/101"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.firstName").value("Jorge"));
        }

        @Test
        void getStudentByName_DebeRetornarLista() throws Exception {
                Student mockStudent = Student.builder().rollNo(101).firstName("Jorge").build();
                when(studentService.getStudentByFirstName("Jorge")).thenReturn(Collections.singletonList(mockStudent));

                mockMvc.perform(get("/student-jpa/firstName/Jorge"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].firstName").value("Jorge"));
        }

        @Test
        void getOneStudentByFirstName_DebeRetornarEstudiante() throws Exception {
                Student mockStudent = Student.builder().rollNo(101).firstName("Jorge").build();
                when(studentService.getOneStudentByFirstName("Jorge")).thenReturn(mockStudent);

                mockMvc.perform(get("/student-jpa/one-by-firstName/Jorge"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.firstName").value("Jorge"));
        }

        @Test
        void getStudentByFirstNameLike_DebeRetornarLista() throws Exception {
                Student mockStudent = Student.builder().rollNo(101).firstName("Jorge").build();
                when(studentService.getStudentByFirstNameLike("%Jor%"))
                                .thenReturn(Collections.singletonList(mockStudent));

                mockMvc.perform(get("/student-jpa/firstName-like/%Jor%"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].firstName").value("Jorge"));
        }

        @Test
        void getStudentBylName_DebeRetornarEstudiante() throws Exception {
                Student mockStudent = Student.builder().rollNo(101).lastName("Candela").build();
                when(studentService.getStudentByLastName("Candela")).thenReturn(mockStudent);

                mockMvc.perform(get("/student-jpa/one-by-lastName/Candela"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.lastName").value("Candela"));
        }

        @Test
        void getStudentBySalaryGreaterThan_DebeRetornarLista() throws Exception {
                Student mockStudent = Student.builder().rollNo(101).firstName("Jorge").build();
                when(studentService.getStudentBySalaryGreaterThan(80))
                                .thenReturn(Collections.singletonList(mockStudent));

                mockMvc.perform(get("/student-jpa/salary-greater-than/80"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].firstName").value("Jorge"));
        }

        @Test
        void getStudentByCondition_DebeRetornarListaPorPost() throws Exception {
                Student mockStudent = Student.builder().rollNo(101).firstName("Jorge").build();
                when(studentService.getStudentByCondition(any(Student.class)))
                                .thenReturn(Collections.singletonList(mockStudent));

                String jsonRequest = "{\"rollNo\": 101}";

                mockMvc.perform(post("/student-jpa/get-by-condition")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].firstName").value("Jorge"));
        }
}