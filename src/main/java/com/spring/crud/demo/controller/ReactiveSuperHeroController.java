package com.spring.crud.demo.controller;

import com.spring.crud.demo.model.SuperHero;
import com.spring.crud.demo.service.ReactiveSuperHeroService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive/super-heroes")
public class ReactiveSuperHeroController {

    @Autowired
    private ReactiveSuperHeroService reactiveSuperHeroService;

    @Operation(summary = "Try this endpoint in chrome, postman doesn't support for reactive programming")
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<SuperHero>> findAll() {
        Flux<SuperHero> list = reactiveSuperHeroService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public Mono<SuperHero> findById(@PathVariable int id) {
        return reactiveSuperHeroService.findById(id);
    }

    @PostMapping
    public Mono<SuperHero> save(@RequestBody SuperHero superHero) {
        return reactiveSuperHeroService.save(superHero);
    }

    @PutMapping("/{id}")
    public Mono<SuperHero> update(
            @PathVariable int id,
            @RequestBody SuperHero superHero) {

        return reactiveSuperHeroService.update(id, superHero);
    }

    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable int id) {
        return reactiveSuperHeroService.delete(id)
                .thenReturn("Deleted successfully...!");
    }
}