package com.standing.controller;

import com.standing.config.AppProperties;
import com.standing.dto.OneDimensionObservationDto;
import com.standing.service.api.ObservationsCodec;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpResponse;
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

    @Get
    @Produces(TEXT_HTML)
    public HttpResponse<?> get(
            @Nullable @QueryValue(value = "observations") String observationsValue,
            @Nullable @QueryValue(value = "l") String leftLimit,
            @Nullable @QueryValue(value = "r") String rightLimit
    ) {
        return HttpResponse.ok(new RockerWritable(views.observationsPage.template(
                expandObservations(getObservations(observationsValue)), leftLimit, rightLimit, properties)
        ));
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
