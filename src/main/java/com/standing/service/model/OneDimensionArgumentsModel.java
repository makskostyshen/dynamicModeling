package com.standing.service.model;

import com.standing.dto.OneDimensionObservationDto;
import lombok.*;

import java.util.List;

@Getter
@Builder
public class OneDimensionArgumentsModel {
    private String modelId;
    private String stateFunctionId;
    private String dimensionsNumberId;
    private String region;
    private String initialObservation;
    private String limitObservations;
    private String generalObservations;
}
