package com.spring.crud.demo.controller;

import com.spring.crud.demo.model.SuperHero;
import com.spring.crud.demo.service.ReactiveSuperHeroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReactiveSuperHeroControllerTest {

    @Mock
    private ReactiveSuperHeroService service;

    @InjectMocks
    private ReactiveSuperHeroController controller;

    private SuperHero superHero;

    @BeforeEach
    void setUp() {
        superHero = SuperHero.builder()
                .id(1)
                .name("Tony")
                .superName("Iron Man")
                .build();
    }

    @Test
    void findAll_debeRetornar200YFlux() {
        when(service.findAll()).thenReturn(Flux.just(superHero));

        ResponseEntity<Flux<SuperHero>> response = controller.findAll();

        assertEquals(200, response.getStatusCodeValue());

        Flux<SuperHero> body = Objects.requireNonNull(response.getBody());

        StepVerifier.create(body)
                .expectNext(superHero)
                .verifyComplete();

        verify(service).findAll();
    }

    @Test
    void findById_debeRetornarSuperHeroe() {
        when(service.findById(1)).thenReturn(Mono.just(superHero));

        StepVerifier.create(controller.findById(1))
                .expectNext(superHero)
                .verifyComplete();

        verify(service).findById(1);
    }

    @Test
    void save_debeRetornarSuperHeroe() {
        when(service.save(superHero)).thenReturn(Mono.just(superHero));

        StepVerifier.create(controller.save(superHero))
                .expectNext(superHero)
                .verifyComplete();

        verify(service).save(superHero);
    }

    @Test
    void update_debeRetornarSuperHeroeActualizado() {
        when(service.update(1, superHero))
                .thenReturn(Mono.just(superHero));

        StepVerifier.create(controller.update(1, superHero))
                .expectNext(superHero)
                .verifyComplete();

        verify(service).update(1, superHero);
    }

    @Test
    void delete_debeRetornarMensaje() {
        when(service.delete(1)).thenReturn(Mono.empty());

        StepVerifier.create(controller.delete(1))
                .expectNext("Deleted successfully...!")
                .verifyComplete();

        verify(service).delete(1);
    }
}