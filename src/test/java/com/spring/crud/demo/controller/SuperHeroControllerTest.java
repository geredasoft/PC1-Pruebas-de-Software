package com.spring.crud.demo.controller;

import com.spring.crud.demo.model.SuperHero;
import com.spring.crud.demo.service.SuperHeroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(MockitoExtension.class)
class SuperHeroControllerTest {

    @Mock
    private SuperHeroService service;

    @InjectMocks
    private SuperHeroController controller;

    private SuperHero superHero;

    @BeforeEach
    void setUp() {
        superHero = SuperHero.builder()
                .id(1)
                .name("Tony")
                .superName("Iron Man")
                .profession("Business man")
                .age(45)
                .canFly(true)
                .build();
    }

    @AfterEach
    void limpiarContextoServlet() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void findAll_debeRetornar200() {
        when(service.findAll()).thenReturn(Collections.singletonList(superHero));

        ResponseEntity<?> response = controller.findAll();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        verify(service).findAll();
    }

    @Test
    void findById_debeRetornar200() {
        when(service.findById(1)).thenReturn(superHero);

        ResponseEntity<?> response = controller.findById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(superHero, response.getBody());
    }

    @Test
    void save_debeRetornar201() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("");

        RequestContextHolder.setRequestAttributes(
                new ServletRequestAttributes(request)
        );

        when(service.save(superHero)).thenReturn(superHero);

        ResponseEntity<?> response = controller.save(superHero);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(superHero, response.getBody());

        verify(service).save(superHero);
    }

    @Test
    void update_debeRetornar200() {
        when(service.update(1, superHero)).thenReturn(superHero);

        ResponseEntity<?> response = controller.update(1, superHero);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(superHero, response.getBody());
    }

    @Test
    void delete_debeRetornar200() {
        doNothing().when(service).delete(1);

        ResponseEntity<?> response = controller.delete(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Deleted successfully...!", response.getBody());

        verify(service).delete(1);
    }
}