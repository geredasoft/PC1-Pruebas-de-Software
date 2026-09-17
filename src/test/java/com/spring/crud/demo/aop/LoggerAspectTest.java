package com.spring.crud.demo.aop;

import com.spring.crud.demo.model.Student;
import com.spring.crud.demo.model.SuperHero;
import com.spring.crud.demo.model.emp.Employee;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class LoggerAspectTest {

    private final LoggerAspect aspect =
            new LoggerAspect();

    @Test
    void logSuperHeroBefore_debeProcesarSuperHero() {

        JoinPoint joinPoint =
                Mockito.mock(JoinPoint.class);

        SuperHero hero = SuperHero.builder()
                .name("Tony")
                .superName("Iron Man")
                .build();

        Mockito.when(joinPoint.getArgs())
                .thenReturn(new Object[]{hero});

        assertDoesNotThrow(
                () -> aspect.logSuperHeroBefore(joinPoint)
        );
    }

    @Test
    void logSuperHeroBefore_debeProcesarEmployee() {

        JoinPoint joinPoint =
                Mockito.mock(JoinPoint.class);

        Employee employee = Employee.builder()
                .firstName("Rahul")
                .lastName("Ghadage")
                .build();

        Mockito.when(joinPoint.getArgs())
                .thenReturn(new Object[]{employee});

        assertDoesNotThrow(
                () -> aspect.logSuperHeroBefore(joinPoint)
        );
    }

    @Test
    void logSuperHeroBefore_debeProcesarStudent() {

        JoinPoint joinPoint =
                Mockito.mock(JoinPoint.class);

        Student student = Student.builder()
                .rollNo(1)
                .firstName("Binay")
                .build();

        Mockito.when(joinPoint.getArgs())
                .thenReturn(new Object[]{student});

        assertDoesNotThrow(
                () -> aspect.logSuperHeroBefore(joinPoint)
        );
    }

    @Test
    void logSuperHeroBefore_debeIgnorarObjetosDesconocidos() {

        JoinPoint joinPoint =
                Mockito.mock(JoinPoint.class);

        Mockito.when(joinPoint.getArgs())
                .thenReturn(new Object[]{"Texto", 10});

        assertDoesNotThrow(
                () -> aspect.logSuperHeroBefore(joinPoint)
        );
    }

    @Test
    void logSuperHeroAfter_debeProcesarResponseEntity200() {

        JoinPoint joinPoint =
                Mockito.mock(JoinPoint.class);

        ResponseEntity<String> response =
                ResponseEntity.ok("resultado");

        Mockito.when(joinPoint.getArgs())
                .thenReturn(new Object[]{});

        assertDoesNotThrow(
                () -> aspect.logSuperHeroAfter(joinPoint, response)
        );
    }

    @Test
    void logSuperHeroAfter_debeProcesarResponseEntityNo200() {

        JoinPoint joinPoint =
                Mockito.mock(JoinPoint.class);

        ResponseEntity<String> response =
                ResponseEntity.badRequest().body("error");

        Mockito.when(joinPoint.getArgs())
                .thenReturn(new Object[]{});

        assertDoesNotThrow(
                () -> aspect.logSuperHeroAfter(joinPoint, response)
        );
    }

    @Test
    void logSuperHeroAfter_conResultadoNull_noDebeLanzarExcepcion() {

        JoinPoint joinPoint =
                Mockito.mock(JoinPoint.class);

        assertDoesNotThrow(
                () -> aspect.logSuperHeroAfter(joinPoint, null)
        );
    }

    @Test
    void logSuperHeroAfter_conResultadoNoResponseEntity_noDebeLanzarExcepcion() {

        JoinPoint joinPoint =
                Mockito.mock(JoinPoint.class);

        assertDoesNotThrow(
                () -> aspect.logSuperHeroAfter(joinPoint, "texto")
        );
    }
}