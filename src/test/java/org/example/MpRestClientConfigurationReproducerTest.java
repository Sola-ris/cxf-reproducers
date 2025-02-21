package org.example;

import java.net.URI;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Test;

public class MpRestClientConfigurationReproducerTest {

    @Test
    void testAccessConfigurationInFilter() {
        GreetingClient client = RestClientBuilder.newBuilder()
            .baseUri(URI.create("http://localhost:8080"))
            .property("foo", "bar")
            .register(Filter.class)
            .build(GreetingClient.class);

        client.greet().close();
    }

    @Path("")
    public interface GreetingClient {

        @GET
        Response greet();
    }

    public static class Filter implements ClientRequestFilter {

        @Override
        public void filter(ClientRequestContext requestContext) {
            System.out.println(requestContext.getConfiguration().getProperty("foo"));
        }
    }
}
