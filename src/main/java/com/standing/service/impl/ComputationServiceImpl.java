package com.standing.service.impl;

import com.standing.dto.ResultDto;
import com.standing.service.api.ComputationService;
import com.standing.service.api.TextFileReader;
import com.standing.service.model.OneDimensionArgumentsModel;
import io.micronaut.context.annotation.Property;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Singleton
@RequiredArgsConstructor
public class ComputationServiceImpl implements ComputationService {

    private final CommandsExecutorImpl executor;

    private final TextFileReader reader;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");

    @Property(name = "app.modeling.solutions.path")
    private String solutionsPath;

    @Property(name = "app.modeling.solutions.graph.file.name")
    private String graphFileName;

    @Property(name = "app.modeling.solutions.function.file.name")
    private String solutionFunctionFileName;


    @Override
    public ResultDto compute(OneDimensionArgumentsModel arguments) {
        String folderName = solutionsPath + LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).format(formatter).toString();

        String solutionFunctionFilePath =  folderName + "/" + solutionFunctionFileName;
        String graphFilePath = folderName + "/" + graphFileName;

        executor.createFolder(folderName);
        executor.executePythonComputing(arguments, solutionFunctionFilePath, graphFilePath);

        try {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(graphFilePath));
            String encodedGraphFile = Base64.getEncoder().encodeToString(fileContent);

            return new ResultDto(
//                graphFilePath.substring(18),
                    encodedGraphFile,
//                "/assets/solutions/29-11-2023-23-10-01/graph.jpg",
//                "/assets/solutions/29-11-2023-23-23-11/graph.jpg",
                    reader.read(solutionFunctionFilePath)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
