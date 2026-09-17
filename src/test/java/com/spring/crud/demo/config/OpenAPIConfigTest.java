package com.spring.crud.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenAPIConfigTest {

    @Test
    void customOpenAPI_debeConfigurarInformacionCorrectamente() {

        OpenAPIConfig config = new OpenAPIConfig();

        OpenAPI openAPI =
                config.customOpenAPI("1.0.0");

        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());

        assertEquals(
                "CRUD API",
                openAPI.getInfo().getTitle()
        );

        assertEquals(
                "1.0.0",
                openAPI.getInfo().getVersion()
        );

        assertEquals(
                "This is a sample CRUD application using spring data",
                openAPI.getInfo().getDescription()
        );

        assertNotNull(openAPI.getInfo().getContact());

        assertEquals(
                "Rahul Ghadage",
                openAPI.getInfo().getContact().getName()
        );
    }
}