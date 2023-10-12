package com.standing.controller;

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
    private static final int INITIAL_OBSERVATIONS_NUMBER = 3;

    private final ObservationsCodec observationsCodec;

    @Get
    @Produces(TEXT_HTML)
    public HttpResponse<?> get(
            @Nullable @QueryValue String observations
    ) {
        if (!StringUtils.isEmpty(observations)) {
            List<OneDimensionObservationDto> given = observationsCodec.decode(observations);
            List<OneDimensionObservationDto> expanded = expandObservations(given);
            return HttpResponse.ok(new RockerWritable(views.observationsPage.template(
                    expanded
            )));
        }
        return HttpResponse.ok(new RockerWritable(views.observationsPage.template(
                expandObservations(List.of(new OneDimensionObservationDto(1, 2, 3)))
        )));

    }

    private List<OneDimensionObservationDto> expandObservations(final List<OneDimensionObservationDto> observations) {
        int size = observations.size();

        if (size < INITIAL_OBSERVATIONS_NUMBER) {
            List<OneDimensionObservationDto> expanded = new ArrayList<>(observations);
            for (int i = 0; i < INITIAL_OBSERVATIONS_NUMBER - size; i++) {
                expanded.add(new OneDimensionObservationDto());
            }
            return expanded;
        }
        return observations;
    }
}
