package com.spring.crud.demo.handler;

import com.spring.crud.demo.model.SuperHero;
import com.spring.crud.demo.service.ReactiveSuperHeroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SuperHeroHandlerTest {

    @Mock
    private ReactiveSuperHeroService service;

    @Mock
    private ServerRequest request;

    @InjectMocks
    private SuperHeroHandler handler;

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
    void findAll_debeRetornarRespuestaOk() {
        when(service.findAll()).thenReturn(Flux.just(superHero));

        StepVerifier.create(handler.findAll(request))
                .assertNext(response ->
                        assertEquals(200, response.statusCode().value())
                )
                .verifyComplete();

        verify(service).findAll();
    }

    @Test
    void findById_debeRetornarRespuestaOk() {
        when(request.pathVariable("id")).thenReturn("1");
        when(service.findById(1)).thenReturn(Mono.just(superHero));

        StepVerifier.create(handler.findById(request))
                .assertNext(response ->
                        assertEquals(200, response.statusCode().value())
                )
                .verifyComplete();

        verify(service).findById(1);
    }

    @Test
    void save_debeGuardarSuperHeroe() {
        when(request.bodyToMono(SuperHero.class))
                .thenReturn(Mono.just(superHero));

        when(service.save(superHero))
                .thenReturn(Mono.just(superHero));

        StepVerifier.create(handler.save(request))
                .assertNext(response ->
                        assertEquals(200, response.statusCode().value())
                )
                .verifyComplete();

        verify(service).save(superHero);
    }

    @Test
    void update_debeActualizarSuperHeroe() {
        when(request.pathVariable("id")).thenReturn("1");

        when(request.bodyToMono(SuperHero.class))
                .thenReturn(Mono.just(superHero));

        when(service.update(1, superHero))
                .thenReturn(Mono.just(superHero));

        StepVerifier.create(handler.update(request))
                .assertNext(response ->
                        assertEquals(200, response.statusCode().value())
                )
                .verifyComplete();

        verify(service).update(1, superHero);
    }

    @Test
    void delete_debeRetornarRespuestaOk() {
        when(request.pathVariable("id")).thenReturn("1");

        when(service.delete(1))
                .thenReturn(Mono.empty());

        StepVerifier.create(handler.delete(request))
                .assertNext(response ->
                        assertEquals(200, response.statusCode().value())
                )
                .verifyComplete();

        verify(service).delete(1);
    }
}