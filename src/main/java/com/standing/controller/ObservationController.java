package com.standing.controller;

import com.standing.PythonExecutor;
import com.standing.config.AppProperties;
import com.standing.dto.ModelAndOneDimensionObservationsDto;
import com.standing.dto.ModelDetailsDto;
import com.standing.dto.OneDimensionObservationDto;
import com.standing.service.api.ObservationsCodec;
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

    private final PythonExecutor executor;
    @Get
    @Produces(TEXT_HTML)
    public HttpResponse<?> get(
            @Nullable @QueryValue String model,
            @Nullable @QueryValue String dimensionsNumber,
            @Nullable @QueryValue String stateFunction,
            @Nullable @QueryValue(value = "observations") String observationsValue,
            @Nullable @QueryValue(value = "l") String leftLimit,
            @Nullable @QueryValue(value = "r") String rightLimit
    ) {
        return HttpResponse.ok(new RockerWritable(
                views.observationsPage.template(
                        new ModelDetailsDto(
                                model,
                                dimensionsNumber,
                                stateFunction
                        ),
                        expandObservations(getObservations(observationsValue)),
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
            ) {
        System.out.println("starting to execute python");

        executor.executeCreatingDirectory();

        System.out.println("finish creating directory");

        return HttpResponse.ok(new RockerWritable(views.resultPage.template()));
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
