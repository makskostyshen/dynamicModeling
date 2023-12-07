package com.standing.service.model;

import lombok.Builder;
import lombok.Getter;

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
    private String maxTime;
}
