package com.standing.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

@Factory
public class ApplicationConfig {

    @Singleton
    public ObjectMapper getMapper() {
        return new ObjectMapper();
    }
}
