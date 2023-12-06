package com.standing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.standing.config.AppProperties;
import com.standing.dto.ModelAndOneDimensionObservationsDto;
import com.standing.dto.ModelDetailsDto;
import com.standing.dto.OneDimensionObservationDto;
import com.standing.dto.ResultDto;
import com.standing.service.api.ComputationService;
import com.standing.service.api.ObservationsCodec;
import com.standing.service.model.OneDimensionArgumentsModel;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.views.rocker.RockerWritable;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static io.micronaut.http.MediaType.TEXT_HTML;

@Controller("/observations")
@RequiredArgsConstructor
public class ObservationController {

    private final ObservationsCodec observationsCodec;

    private final AppProperties properties;

    private final ComputationService computationService;

    private final ObjectMapper objectMapper;
    @Get
    @Produces(TEXT_HTML)
    public HttpResponse<?> get(
            @Nullable @QueryValue String model,
            @Nullable @QueryValue String dimensionsNumber,
            @Nullable @QueryValue String stateFunction,
            @Nullable @QueryValue(value = "observations") String observationsValue,
            @Nullable @QueryValue(value = "in") String initialObservation,
            @Nullable @QueryValue(value = "l") String leftLimitObservation,
            @Nullable @QueryValue(value = "r") String rightLimitObservation,
            @Nullable @QueryValue(value = "xMin") String leftLimit,
            @Nullable @QueryValue(value = "xMax") String rightLimit
    ) {
        return HttpResponse.ok(new RockerWritable(
                views.observationsPage.template(
                        new ModelDetailsDto(
                                model,
                                dimensionsNumber,
                                stateFunction
                        ),
                        expandObservations(getObservations(observationsValue)),
                        initialObservation != null ? observationsCodec.decode(initialObservation).get(0) : new OneDimensionObservationDto(),
                        leftLimitObservation != null ? observationsCodec.decode(leftLimitObservation).get(0) : new OneDimensionObservationDto(),
                        rightLimitObservation != null ? observationsCodec.decode(rightLimitObservation).get(0) : new OneDimensionObservationDto(),
                        leftLimit,
                        rightLimit,
                        properties)
        ));
    }

    @Post
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(TEXT_HTML)
    public HttpResponse<?> post(
            @Nullable @Body ModelAndOneDimensionObservationsDto dto
            ) throws JsonProcessingException {
        ResultDto result = computationService.compute(
                OneDimensionArgumentsModel.builder()
                        .modelId(dto.getModel())
                        .stateFunctionId(dto.getStateFunction())
                        .dimensionsNumberId(dto.getDimensionsNumber())
                        .region(List.of(dto.getXMin(), dto.getXMax()).toString())
                        .initialObservation(
                                objectMapper.writeValueAsString(
                                        observationsCodec.decode(dto.getIn()).get(0))
                        )
                        .limitObservations(
                                objectMapper.writeValueAsString(
                                        List.of(
                                                observationsCodec.decode(dto.getL()).get(0),
                                                observationsCodec.decode(dto.getR()).get(0)
                                        )
                                )

                        )
                        .generalObservations(
                                objectMapper.writeValueAsString(
                                        observationsCodec.decode(dto.getObservations())
                                )
                        )
                        .build());

        return HttpResponse.ok(new RockerWritable(views.resultPage.template(
                new ResultDto(
                        result.getGraphPath(),
                        result.getFunction()
                )
        )));
    }

    private List<OneDimensionObservationDto> expandObservations(final List<OneDimensionObservationDto> observations) {
        int size = observations.size();

        if (size < properties.getMinObservationsNumber()) {
            List<OneDimensionObservationDto> expanded = new ArrayList<>(observations);
            for (int i = 0; i < properties.getMinObservationsNumber() - size; i++) {
                expanded.add(new OneDimensionObservationDto());
            }
            return expanded;
        }
        return observations;
    }

    private List<OneDimensionObservationDto> getObservations(final String observationsValue) {
        return !StringUtils.isEmpty(observationsValue)
            ? observationsCodec.decode(observationsValue)
            : List.of();
    }
}
