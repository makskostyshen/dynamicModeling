package com.standing.controller;

import com.standing.service.impl.CommandsExecutorImpl;
import com.standing.dto.ModelDetailsDto;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.views.rocker.RockerWritable;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static io.micronaut.http.MediaType.TEXT_HTML;

@Controller("/model")
@RequiredArgsConstructor
public class ModelController {

    private final CommandsExecutorImpl executor;
    @Get
    @Produces(TEXT_HTML)
    public HttpResponse<?> get(
            @Nullable @QueryValue String model,
            @Nullable @QueryValue String dimensionsNumber,
            @Nullable @QueryValue String stateFunction
    ) {
        return HttpResponse.ok(new RockerWritable(views.modelPage.template(
                new ModelDetailsDto(model, dimensionsNumber, stateFunction)
        )));
    }

    @Post
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(TEXT_HTML)
    public  HttpResponse<?> post(@Body Map<String, String> args) {
        System.out.println(args);

        return HttpResponse.ok(new RockerWritable(views.modelPage.template(new ModelDetailsDto())));
    }
}
