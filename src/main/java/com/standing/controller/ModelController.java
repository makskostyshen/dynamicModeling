package com.standing.controller;

import com.standing.PythonExecutor;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.views.rocker.RockerWritable;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static io.micronaut.http.MediaType.TEXT_HTML;

@Controller
@RequiredArgsConstructor
public class ModelController {

    private final PythonExecutor executor;

    @Get
    @Produces(TEXT_HTML)
    public HttpResponse<?> get() {
        return HttpResponse.ok(new RockerWritable(views.modelPage.template()));
    }

    @Post
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(TEXT_HTML)
    public  HttpResponse<?> post(@Body Map<String, String> args) {
        System.out.println(args);

        executor.executeCreatingDirectory();

        return HttpResponse.ok(new RockerWritable(views.modelPage.template()));
    }
}
