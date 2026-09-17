package com.spring.crud.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class DefaultControllerTest {

    private final DefaultController controller =
            new DefaultController();

    @Test
    void redirect_debeRedirigirASwagger() {
        ResponseEntity<Void> response = controller.redirect();

        assertEquals(302, response.getStatusCodeValue());
        assertNotNull(response.getHeaders().getLocation());
        assertEquals(
                "swagger-ui-custom.html",
                response.getHeaders().getLocation().toString()
        );
    }

    @Test
    void databaseUrl_debeRedirigirAH2Console() {
        ResponseEntity<Void> response = controller.databaseUrl();

        assertEquals(302, response.getStatusCodeValue());
        assertNotNull(response.getHeaders().getLocation());
        assertEquals(
                "h2-console",
                response.getHeaders().getLocation().toString()
        );
    }
}