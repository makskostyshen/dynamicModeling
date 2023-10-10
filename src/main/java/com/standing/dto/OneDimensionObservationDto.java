package com.standing.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Introspected
@Serdeable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneDimensionObservationDto {
    private String arg;
    private String val;
    private String time;
}
