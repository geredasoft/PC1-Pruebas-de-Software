package com.spring.crud.demo.annotation;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.junit.jupiter.api.Assertions.*;

class LoggingAnnotationsTest {

    @Test
    void logObjectBefore_debeTenerRetentionRuntime() {

        Retention retention =
                LogObjectBefore.class.getAnnotation(Retention.class);

        assertNotNull(retention);
        assertEquals(
                RetentionPolicy.RUNTIME,
                retention.value()
        );
    }

    @Test
    void logObjectAfter_debeTenerRetentionRuntime() {

        Retention retention =
                LogObjectAfter.class.getAnnotation(Retention.class);

        assertNotNull(retention);
        assertEquals(
                RetentionPolicy.RUNTIME,
                retention.value()
        );
    }

    @Test
    void logObjectBefore_debePermitirMetodosYClases() {

        Target target =
                LogObjectBefore.class.getAnnotation(Target.class);

        assertNotNull(target);

        assertTrue(
                java.util.Arrays.asList(target.value())
                        .contains(java.lang.annotation.ElementType.METHOD)
        );

        assertTrue(
                java.util.Arrays.asList(target.value())
                        .contains(java.lang.annotation.ElementType.TYPE)
        );
    }

    @Test
    void logObjectAfter_debePermitirMetodosYClases() {

        Target target =
                LogObjectAfter.class.getAnnotation(Target.class);

        assertNotNull(target);

        assertTrue(
                java.util.Arrays.asList(target.value())
                        .contains(java.lang.annotation.ElementType.METHOD)
        );

        assertTrue(
                java.util.Arrays.asList(target.value())
                        .contains(java.lang.annotation.ElementType.TYPE)
        );
    }
}