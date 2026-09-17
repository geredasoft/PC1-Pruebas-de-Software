package com.spring.crud.demo.utils;

import com.spring.crud.demo.model.Student;
import com.spring.crud.demo.model.SuperHero;
import com.spring.crud.demo.model.emp.Employee;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HelperUtilTest {

    @Test
    void studentSupplier_debeGenerarDiezEstudiantes() {
        List<Student> students = HelperUtil.studentSupplier.get();

        assertNotNull(students);
        assertEquals(10, students.size());

        assertEquals(1, students.get(0).getRollNo());
        assertEquals("Binay", students.get(0).getFirstName());
        assertEquals("Gurung", students.get(0).getLastName());
        assertEquals(300.0f, students.get(0).getMarks());
    }

    @Test
    void superHeroesSupplier_debeGenerarCincoSuperHeroes() {
        List<SuperHero> heroes = HelperUtil.superHeroesSupplier.get();

        assertNotNull(heroes);
        assertEquals(5, heroes.size());

        assertEquals("Wade", heroes.get(0).getName());
        assertEquals("Deadpool", heroes.get(0).getSuperName());

        assertEquals("Tony", heroes.get(3).getName());
        assertTrue(heroes.get(3).isCanFly());
    }

    @Test
    void employeeSupplier_debeGenerarDosEmpleados() {
        List<Employee> employees = HelperUtil.employeeSupplier.get();

        assertNotNull(employees);
        assertEquals(2, employees.size());

        Employee rahul = employees.get(0);

        assertEquals("Rahul", rahul.getFirstName());
        assertEquals("Ghadage", rahul.getLastName());
        assertEquals(28, rahul.getAge());

        assertNotNull(rahul.getAddress());
        assertEquals("Pune", rahul.getAddress().getCity());

        assertNotNull(rahul.getPhoneNumbers());
        assertEquals(1, rahul.getPhoneNumbers().size());

        assertNotNull(rahul.getHobbies());
        assertEquals(2, rahul.getHobbies().size());
    }
}