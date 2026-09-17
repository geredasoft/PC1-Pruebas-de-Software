package com.spring.crud.demo.service;

import com.spring.crud.demo.model.SuperHero;
import com.spring.crud.demo.repository.SuperHeroRepository;
import com.spring.crud.demo.service.impl.SuperHeroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuperHeroServiceImplTest {

    @Mock
    private SuperHeroRepository repository;

    @InjectMocks
    private SuperHeroServiceImpl service;

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

    @Test
    void findAll_debeRetornarTodosLosSuperHeroes() {
        when(repository.findAll()).thenReturn(
                Collections.singletonList(superHero)
        );

        List<SuperHero> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals(superHero, result.get(0));

        verify(repository).findAll();
    }

    @Test
    void findAll_debeRetornarListaVacia() {
        when(repository.findAll()).thenReturn(
                Collections.emptyList()
        );

        List<SuperHero> result = service.findAll();

        assertTrue(result.isEmpty());

        verify(repository).findAll();
    }

    @Test
    void findById_debeRetornarSuperHeroeExistente() {
        when(repository.findById(1)).thenReturn(Optional.of(superHero));

        SuperHero result = service.findById(1);

        assertNotNull(result);
        assertEquals("Iron Man", result.getSuperName());

        verify(repository).findById(1);
    }

    @Test
    void findById_debeLanzarExcepcionSiNoExiste() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.findById(99)
        );

        verify(repository).findById(99);
    }

    @Test
    void save_debeGuardarSuperHeroe() {
        when(repository.save(superHero)).thenReturn(superHero);

        SuperHero result = service.save(superHero);

        assertEquals(superHero, result);

        verify(repository).save(superHero);
    }

    @Test
    void update_debeActualizarSuperHeroe() {
        SuperHero updated = SuperHero.builder()
                .name("Peter")
                .superName("Spider Man")
                .profession("Student")
                .age(21)
                .canFly(true)
                .build();

        when(repository.findById(1)).thenReturn(Optional.of(superHero));
        when(repository.save(updated)).thenReturn(updated);

        SuperHero result = service.update(1, updated);

        assertEquals(1, updated.getId());
        assertEquals(updated, result);

        verify(repository).findById(1);
        verify(repository).save(updated);
    }

    @Test
    void update_debeLanzarExcepcionSiNoExiste() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.update(99, superHero)
        );

        verify(repository).findById(99);
        verify(repository, never()).save(any(SuperHero.class));
    }

    @Test
    void delete_debeEliminarSuperHeroeExistente() {
        when(repository.findById(1)).thenReturn(Optional.of(superHero));

        service.delete(1);

        verify(repository).findById(1);
        verify(repository).delete(superHero);
    }

    @Test
    void delete_noDebeEliminarSiNoExiste() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        service.delete(99);

        verify(repository).findById(99);
        verify(repository, never()).delete(any(SuperHero.class));
    }
}