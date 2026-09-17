package com.spring.crud.demo.service.impl;

import com.spring.crud.demo.model.SuperHero;
import com.spring.crud.demo.repository.SuperHeroRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.webjars.NotFoundException;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuperHeroServiceImplTest {
    @Mock
    private SuperHeroRepository repository;

    @InjectMocks
    private SuperHeroServiceImpl service;


    @Test
    void debeObtenerTodosLosSuperheroes() {

        // ARRANGE
        SuperHero hero1 = SuperHero.builder()
                .id(1)
                .name("Peter Parker")
                .superName("Spider-Man")
                .profession("Fotógrafo")
                .age(25)
                .canFly(false)
                .build();

        SuperHero hero2 = SuperHero.builder()
                .id(2)
                .name("Tony Stark")
                .superName("Iron Man")
                .profession("Ingeniero")
                .age(40)
                .canFly(true)
                .build();

        when(repository.findAll())
                .thenReturn(Arrays.asList(hero1, hero2));

        // ACT
        List<SuperHero> resultado = service.findAll();

        // ASSERT
        assertEquals(2, resultado.size());
        assertEquals("Spider-Man", resultado.get(0).getSuperName());
        assertEquals("Iron Man", resultado.get(1).getSuperName());
    }

    @Test
    void debeObtenerSuperHeroePorId() {

        // ARRANGE
        SuperHero hero = SuperHero.builder()
                .id(1)
                .name("Peter Parker")
                .superName("Spider-Man")
                .profession("Fotógrafo")
                .age(25)
                .canFly(false)
                .build();

        when(repository.findById(1))
                .thenReturn(Optional.of(hero));

        // ACT
        SuperHero resultado = service.findById(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Spider-Man", resultado.getSuperName());
    }

    @Test
    void debeLanzarExcepcionCuandoNoExisteSuperHeroe() {

        // ARRANGE
        when(repository.findById(1))
                .thenReturn(Optional.empty());


        // ACT + ASSERT
        assertThrows(NotFoundException.class, () -> {
            service.findById(1);
        });
    }

    @Test
    void debeGuardarSuperHeroe() {

        // ARRANGE
        SuperHero hero = SuperHero.builder()
                .name("Clark Kent")
                .superName("Superman")
                .profession("Periodista")
                .age(30)
                .canFly(true)
                .build();

        when(repository.save(hero))
                .thenReturn(hero);

        // ACT
        SuperHero resultado = service.save(hero);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("Superman", resultado.getSuperName());
        assertTrue(resultado.isCanFly());
    }

    @Test
    void debeActualizarSuperHeroe() {

        // ARRANGE
        SuperHero heroExistente = SuperHero.builder()
                .id(1)
                .name("Peter Parker")
                .superName("Spider-Man")
                .profession("Fotógrafo")
                .age(25)
                .canFly(false)
                .build();

        SuperHero heroActualizado = SuperHero.builder()
                .name("Peter Parker")
                .superName("Spider-Man")
                .profession("Científico")
                .age(26)
                .canFly(false)
                .build();

        when(repository.findById(1))
                .thenReturn(Optional.of(heroExistente));

        when(repository.save(heroActualizado))
                .thenReturn(heroActualizado);


        // ACT
        SuperHero resultado = service.update(1, heroActualizado);


        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, heroActualizado.getId());
        assertEquals("Científico", resultado.getProfession());

        verify(repository).save(heroActualizado);
    }

    @Test
    void debeLanzarExcepcionAlActualizarSuperHeroeInexistente() {

        // ARRANGE
        SuperHero hero = SuperHero.builder()
                .name("Bruce Wayne")
                .superName("Batman")
                .profession("Empresario")
                .age(35)
                .canFly(false)
                .build();

        when(repository.findById(1))
                .thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThrows(NotFoundException.class, () -> {
            service.update(1, hero);
        });
    }

    @Test
    void debeEliminarSuperHeroe() {

        // ARRANGE
        SuperHero hero = SuperHero.builder()
                .id(1)
                .name("Bruce Wayne")
                .superName("Batman")
                .profession("Empresario")
                .age(35)
                .canFly(false)
                .build();

        when(repository.findById(1))
                .thenReturn(Optional.of(hero));

        // ACT
        service.delete(1);

        // ASSERT
        verify(repository).delete(hero);
    }

    @Test
    void noDebeEliminarSiSuperHeroeNoExiste() {
        // ARRANGE
        when(repository.findById(1))
                .thenReturn(Optional.empty());

        // ACT
        service.delete(1);
        // ASSERT
        verify(repository, never()).delete(any(SuperHero.class));
    }
}
