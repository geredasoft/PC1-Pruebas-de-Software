package com.spring.crud.demo.controller;

import com.spring.crud.demo.model.SuperHero;
import com.spring.crud.demo.service.SuperHeroService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuperHeroControllerTest {

    @Mock
    private SuperHeroService superHeroService;

    @InjectMocks
    private SuperHeroController controller;


    @Test
    void debeObtenerTodosLosSuperheroes() {
        // ARRANGE
        SuperHero superHero1 = new SuperHero();
        superHero1.setId(1);
        superHero1.setName("Bruce Wayne");
        superHero1.setSuperName("Batman");

        SuperHero superHero2 = new SuperHero();
        superHero2.setId(2);
        superHero2.setName("Clark Kent");
        superHero2.setSuperName("Superman");

        List<SuperHero> superheroes =
                Arrays.asList(superHero1, superHero2);

        when(superHeroService.findAll())
                .thenReturn(superheroes);

        // ACT
        ResponseEntity<List<?>> response = controller.findAll();

        // ASSERT
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(superheroes, response.getBody());

        verify(superHeroService).findAll();
    }


    @Test
    void debeObtenerSuperHeroePorId() {
        // ARRANGE
        SuperHero superHero = new SuperHero();
        superHero.setId(1);
        superHero.setName("Bruce Wayne");
        superHero.setSuperName("Batman");

        when(superHeroService.findById(1))
                .thenReturn(superHero);

        // ACT
        ResponseEntity<?> response = controller.findById(1);

        // ASSERT
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(superHero, response.getBody());

        verify(superHeroService).findById(1);
    }


    @Test
    void debeGuardarSuperHeroe() {
        // ARRANGE
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("/super-heroes");

        RequestContextHolder.setRequestAttributes(
                new ServletRequestAttributes(request)
        );

        SuperHero superHero = new SuperHero();
        superHero.setId(1);
        superHero.setName("Bruce Wayne");
        superHero.setSuperName("Batman");

        when(superHeroService.save(superHero))
                .thenReturn(superHero);

        // ACT
        ResponseEntity<?> response = controller.save(superHero);

        // ASSERT
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(superHero, response.getBody());
        assertNotNull(response.getHeaders().getLocation());

        verify(superHeroService).save(superHero);

        // LIMPIAR
        RequestContextHolder.resetRequestAttributes();
    }


    @Test
    void debeActualizarSuperHeroe() {
        // ARRANGE
        SuperHero superHero = new SuperHero();
        superHero.setId(1);
        superHero.setName("Bruce Wayne");
        superHero.setSuperName("Batman");

        when(superHeroService.update(1, superHero))
                .thenReturn(superHero);

        // ACT
        ResponseEntity<?> response =
                controller.update(1, superHero);

        // ASSERT
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(superHero, response.getBody());

        verify(superHeroService).update(1, superHero);
    }


    @Test
    void debeEliminarSuperHeroe() {
        // ARRANGE
        doNothing().when(superHeroService).delete(1);

        // ACT
        ResponseEntity<?> response = controller.delete(1);

        // ASSERT
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(
                "Deleted successfully...!",
                response.getBody()
        );

        verify(superHeroService).delete(1);
    }
}