package com.standing.service.impl;

import com.standing.dto.ResultDto;
import com.standing.service.api.ComputationService;
import com.standing.service.api.MediaFileReader;
import com.standing.service.api.TextFileReader;
import com.standing.service.model.OneDimensionArgumentsModel;
import io.micronaut.context.annotation.Property;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Singleton
@RequiredArgsConstructor
public class ComputationServiceImpl implements ComputationService {
    private final MediaFileReader mediaFileReader;

    private final CommandsExecutorImpl executor;

    private final TextFileReader textFileReader;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");

    @Property(name = "app.modeling.solutions.path")
    private String solutionsPath;

    @Property(name = "app.modeling.solutions.graph.file.name")
    private String graphFileName;

    @Property(name = "app.modeling.solutions.function.file.name")
    private String solutionFunctionFileName;


    @Override
    public ResultDto compute(OneDimensionArgumentsModel arguments) {
        String folderName = solutionsPath + LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).format(formatter);

        String solutionFunctionFilePath =  folderName + "/" + solutionFunctionFileName;
        String graphFilePath = folderName + "/" + graphFileName;

        executor.createFolder(folderName);
        executor.executePythonComputing(arguments, solutionFunctionFilePath, graphFilePath);

        return new ResultDto(
                mediaFileReader.read(graphFilePath),
                textFileReader.read(solutionFunctionFilePath)
        );
    }
}
