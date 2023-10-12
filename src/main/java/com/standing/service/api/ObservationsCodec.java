package com.standing.service.api;

import com.standing.dto.OneDimensionObservationDto;

import java.util.List;

public interface ObservationsCodec {
    List<OneDimensionObservationDto> decode(final String observationsValue);
}
