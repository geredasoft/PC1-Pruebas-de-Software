package com.spring.crud.demo.config;

import org.junit.jupiter.api.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

class CORSFilterTest {

    @Test
    void doFilter_debeConfigurarHeadersCors() throws Exception {

        CORSFilter filter = new CORSFilter();

        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletRequest request = mock(ServletRequest.class);
        FilterChain chain = mock(FilterChain.class);

        filter.doFilter(request, response, chain);

        verify(response).setHeader(
                "Access-Control-Allow-Origin",
                "*"
        );

        verify(response).setHeader(
                "Access-Control-Allow-Methods",
                "POST, GET, PUT, OPTIONS, DELETE, PATCH"
        );

        verify(response).setHeader(
                "Access-Control-Max-Age",
                "3600"
        );

        verify(response).setHeader(
                "Access-Control-Allow-Headers",
                "Origin, Content-Type, Accept"
        );

        verify(response).setHeader(
                "Access-Control-Expose-Headers",
                "Location"
        );

        verify(chain).doFilter(request, response);
    }

    @Test
    void init_noDebeLanzarExcepcion() throws Exception {

        CORSFilter filter = new CORSFilter();

        filter.init(null);
    }

    @Test
    void destroy_noDebeLanzarExcepcion() {

        CORSFilter filter = new CORSFilter();

        filter.destroy();
    }
}