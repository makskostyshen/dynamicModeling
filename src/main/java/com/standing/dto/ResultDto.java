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
public class ResultDto {
    private String graphPath;
    private String function;
}
