package com.spring.crud.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.eq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.webjars.NotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.crud.demo.model.SuperHero;
import com.spring.crud.demo.service.SuperHeroService;

@SpringBootTest
@AutoConfigureMockMvc
public class SuperHeroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SuperHeroService superHeroService;

    private SuperHero mockHero;

    private SuperHero buildHero() {
        return SuperHero.builder()
                .id(1).name("Tony").superName("Iron Man").profession("Business")
                .age(50).canFly(true).build();
    }

    @Test
    void findAll_DebeRetornarLista() throws Exception {
        mockHero = buildHero();
        doReturn(Arrays.asList(mockHero)).when(superHeroService).findAll();

        mockMvc.perform(get("/super-heroes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].superName").value("Iron Man"));
    }

    @Test
    void findById_CuandoExiste_DebeRetornarHeroe() throws Exception {
        mockHero = buildHero();
        when(superHeroService.findById(1)).thenReturn(mockHero);

        mockMvc.perform(get("/super-heroes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tony"));
    }

@Test
void findById_CuandoNoExiste_DebePropagarExcepcion() {
    when(superHeroService.findById(999)).thenThrow(new NotFoundException("no existe"));

    Exception exception = assertThrows(Exception.class, () ->
            mockMvc.perform(get("/super-heroes/999")));

    assertTrue(exception.getCause() instanceof NotFoundException);
}

    @Test
    void save_DebeCrearHeroeYRetornar201() throws Exception {
        mockHero = buildHero();
        when(superHeroService.save(any(SuperHero.class))).thenReturn(mockHero);

        mockMvc.perform(post("/super-heroes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockHero)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Tony"));
    }

    @Test
    void update_CuandoExiste_DebeActualizarYRetornar200() throws Exception {
        SuperHero actualizado = SuperHero.builder()
                .id(1).name("Tony Actualizado").superName("Iron Man")
                .profession("Business").age(51).canFly(true).build();
        when(superHeroService.update(eq(1), any(SuperHero.class))).thenReturn(actualizado);

        mockMvc.perform(put("/super-heroes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tony Actualizado"));
    }

@Test
void update_CuandoNoExiste_DebePropagarExcepcion() {
    SuperHero cambios = SuperHero.builder().name("Nadie").build();
    when(superHeroService.update(eq(999), any(SuperHero.class)))
            .thenThrow(new NotFoundException("no existe"));

    Exception exception = assertThrows(Exception.class, () ->
            mockMvc.perform(put("/super-heroes/999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(cambios))));

    assertTrue(exception.getCause() instanceof NotFoundException);
}

    @Test
    void delete_DebeRetornarMensajeDeExito() throws Exception {
        mockMvc.perform(delete("/super-heroes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted successfully...!"));
    }
}