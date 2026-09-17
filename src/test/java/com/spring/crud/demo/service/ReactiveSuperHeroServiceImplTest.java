package com.spring.crud.demo.service;

import com.spring.crud.demo.model.SuperHero;
import com.spring.crud.demo.repository.SuperHeroRepository;
import com.spring.crud.demo.service.impl.ReactiveSuperHeroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReactiveSuperHeroServiceImplTest {

    @Mock
    private SuperHeroRepository repository;

    @InjectMocks
    private ReactiveSuperHeroServiceImpl service;

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
    void findAll_debeRetornarFluxDeSuperHeroes() {

        SuperHero second = SuperHero.builder()
                .id(2)
                .name("Peter")
                .superName("Spider Man")
                .build();

        when(repository.findAll())
                .thenReturn(Arrays.asList(superHero, second));

        Flux<SuperHero> result = service.findAll();

        StepVerifier.create(result)
                .expectNext(superHero)
                .expectNext(second)
                .verifyComplete();

        verify(repository).findAll();
    }

    @Test
    void findAll_sinDatos_debeCompletarSinElementos() {

        when(repository.findAll())
                .thenReturn(Collections.emptyList());

        StepVerifier.create(service.findAll())
                .verifyComplete();

        verify(repository).findAll();
    }

    @Test
    void findById_debeRetornarMonoConSuperHeroe() {

        when(repository.findById(1))
                .thenReturn(Optional.of(superHero));

        StepVerifier.create(service.findById(1))
                .assertNext(result -> {
                    assertEquals(1, result.getId());
                    assertEquals("Iron Man", result.getSuperName());
                })
                .verifyComplete();

        verify(repository).findById(1);
    }

    @Test
    void findById_siNoExiste_debeLanzarNotFoundException() {

        when(repository.findById(99))
                .thenReturn(Optional.empty());

        StepVerifier.create(service.findById(99))
                .expectError(NotFoundException.class)
                .verify();

        verify(repository).findById(99);
    }

    @Test
    void save_debeRetornarSuperHeroeGuardado() {

        when(repository.save(superHero))
                .thenReturn(superHero);

        StepVerifier.create(service.save(superHero))
                .expectNext(superHero)
                .verifyComplete();

        verify(repository).save(superHero);
    }

    @Test
    void update_debeActualizarSuperHeroe() {

        SuperHero updated = SuperHero.builder()
                .name("Peter")
                .superName("Spider Man")
                .build();

        when(repository.findById(1))
                .thenReturn(Optional.of(superHero));

        when(repository.save(updated))
                .thenReturn(updated);

        StepVerifier.create(service.update(1, updated))
                .assertNext(result -> {
                    assertEquals(1, result.getId());
                    assertEquals("Spider Man", result.getSuperName());
                })
                .verifyComplete();

        verify(repository).findById(1);
        verify(repository).save(updated);
    }

    @Test
    void update_siNoExiste_debeLanzarNotFoundException() {

        when(repository.findById(99))
                .thenReturn(Optional.empty());

        StepVerifier.create(service.update(99, superHero))
                .expectError(NotFoundException.class)
                .verify();

        verify(repository).findById(99);
        verify(repository, never()).save(any(SuperHero.class));
    }

    @Test
    void delete_siExiste_debeEliminar() {

        when(repository.findById(1))
                .thenReturn(Optional.of(superHero));

        StepVerifier.create(service.delete(1))
                .verifyComplete();

        verify(repository).findById(1);
        verify(repository).delete(superHero);
    }

    @Test
    void delete_siNoExiste_noDebeEliminar() {

        when(repository.findById(99))
                .thenReturn(Optional.empty());

        StepVerifier.create(service.delete(99))
                .verifyComplete();

        verify(repository).findById(99);
        verify(repository, never()).delete(any(SuperHero.class));
    }
}