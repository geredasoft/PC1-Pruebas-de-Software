package com.spring.crud.demo.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;

import com.spring.crud.demo.model.SuperHero;
import com.spring.crud.demo.repository.SuperHeroRepository;
import com.spring.crud.demo.service.impl.SuperHeroServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SuperHeroServiceImplTest {

    @Mock
    private SuperHeroRepository superHeroRepository;

    @InjectMocks
    private SuperHeroServiceImpl superHeroService;

    private SuperHero mockHero;

    @BeforeEach
    void setUp() {
        mockHero = SuperHero.builder()
                .id(1).name("Tony").superName("Iron Man").profession("Business")
                .age(50).canFly(true).build();
    }

    @Test
    void findAll_DebeRetornarListaDeHeroes() {
        when(superHeroRepository.findAll()).thenReturn(Arrays.asList(mockHero));
        List<SuperHero> resultado = superHeroService.findAll();
        assertEquals(1, resultado.size());
    }

    @Test
    void findById_CuandoExiste_DebeRetornarHeroe() {
        when(superHeroRepository.findById(1)).thenReturn(Optional.of(mockHero));
        SuperHero resultado = superHeroService.findById(1);
        assertEquals("Iron Man", resultado.getSuperName());
    }

    @Test
    void findById_CuandoNoExiste_DebeLanzarExcepcion() {
        when(superHeroRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> superHeroService.findById(999));
    }

    @Test
    void save_DebeGuardarYRetornarHeroe() {
        when(superHeroRepository.save(mockHero)).thenReturn(mockHero);
        SuperHero resultado = superHeroService.save(mockHero);
        assertEquals("Tony", resultado.getName());
        verify(superHeroRepository, times(1)).save(mockHero);
    }

    @Test
    void update_CuandoExiste_DebeActualizarYRetornarHeroe() {
        SuperHero cambios = SuperHero.builder()
                .name("Tony Actualizado").superName("Iron Man").profession("Business")
                .age(51).canFly(true).build();

        when(superHeroRepository.findById(1)).thenReturn(Optional.of(mockHero));
        when(superHeroRepository.save(any(SuperHero.class))).thenReturn(cambios);

        SuperHero resultado = superHeroService.update(1, cambios);

        assertEquals(1, cambios.getId()); // el service debe setear el id antes de guardar
        assertEquals("Tony Actualizado", resultado.getName());
    }

    @Test
    void update_CuandoNoExiste_DebeLanzarExcepcionYNoGuardar() {
        SuperHero cambios = SuperHero.builder().name("Nadie").build();
        when(superHeroRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> superHeroService.update(999, cambios));
        verify(superHeroRepository, never()).save(any(SuperHero.class));
    }

    @Test
    void delete_CuandoExiste_DebeEliminarHeroe() {
        when(superHeroRepository.findById(1)).thenReturn(Optional.of(mockHero));
        superHeroService.delete(1);
        verify(superHeroRepository, times(1)).delete(mockHero);
    }

    @Test
    void delete_CuandoNoExiste_NoDebeEliminarNada() {
        when(superHeroRepository.findById(999)).thenReturn(Optional.empty());
        superHeroService.delete(999);
        verify(superHeroRepository, never()).delete(any(SuperHero.class));
    }
}