package com.standing.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.standing.dto.OneDimensionObservationDto;
import com.standing.service.api.ObservationsCodec;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Singleton
@RequiredArgsConstructor
public class ObservationsCodecImpl implements ObservationsCodec {

    private final ObjectMapper objectMapper;

    @Override
    public List<OneDimensionObservationDto> decode(String observationsValue) {

        String decoded = new String(
                Base64.getDecoder().decode(observationsValue),
                StandardCharsets.UTF_8);

        try {
            return objectMapper.readValue(decoded, new TypeReference<List<OneDimensionObservationDto>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }
}
