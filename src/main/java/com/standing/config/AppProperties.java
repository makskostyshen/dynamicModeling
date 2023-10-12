package com.standing.config;

import io.micronaut.context.annotation.Property;
import jakarta.inject.Singleton;
import lombok.Getter;

@Getter
@Singleton
public class AppProperties {

    @Property(name="app.modeling.observations.min-number", defaultValue = "3")
    private int minObservationsNumber;
}
