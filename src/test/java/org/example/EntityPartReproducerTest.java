package org.example;

import java.io.IOException;

import jakarta.ws.rs.core.EntityPart;

import org.junit.jupiter.api.Test;

public class EntityPartReproducerTest {

    @Test
    void testBuildEntityPart() throws IOException {
        EntityPart.withName("greeting")
            .content("hello")
            .build();
    }
}
