package org.example;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;

import org.junit.jupiter.api.Test;

public class RemovePropertyReproducerTest {

    @Test
    void testRemoveProperty() {
        try (Client client = ClientBuilder.newClient()) {
            client.property("greeting", "hello");
            client.property("greeting", null);

            client.target("").request().get().close();
        }
    }
}
