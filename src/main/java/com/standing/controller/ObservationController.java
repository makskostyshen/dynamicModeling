package com.standing.controller;

import com.standing.dto.OneDimensionObservationDto;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.views.rocker.RockerWritable;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import static io.micronaut.http.MediaType.TEXT_HTML;

@Controller("/ss")
@RequiredArgsConstructor
public class ObservationController {
    private static final int INITIAL_OBSERVATIONS_NUMBER = 3;

    @Get
    @Produces(TEXT_HTML)
    public HttpResponse<?> get() {
        return HttpResponse.ok(new RockerWritable(views.observationsPage.template(
                List.of(
                        new OneDimensionObservationDto(
                                "x1",
                                "y1",
                                "t1"
                        ),
                        new OneDimensionObservationDto(
                                "x2",
                                "y2",
                                "t2"
                        ),
                        new OneDimensionObservationDto()
                )
        )));
    }

//    @Post
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces(TEXT_HTML)
//    public  HttpResponse<?> post(@Body Map<String, String> args) {
//        System.out.println(args);
//
//        executor.executeCreatingDirectory();
//
//        return HttpResponse.ok(new RockerWritable(views.helloPage.template()));
//    }
}
