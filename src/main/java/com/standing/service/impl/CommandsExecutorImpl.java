package com.standing.service.impl;

import com.standing.service.api.CommandsExecutor;
import com.standing.service.model.OneDimensionArgumentsModel;
import io.micronaut.context.annotation.Property;
import jakarta.inject.Singleton;

import java.io.File;
import java.io.IOException;

@Singleton
public class CommandsExecutorImpl implements CommandsExecutor {
    private static final String PYTHON_COMMAND = "python";

    @Property(name = "app.modeling.python.create-folder.path")
    private String createFolderPythonScriptPath;

    @Override
    public void createFolder(final String folderName) {
        new File(folderName).mkdirs();
    }

    @Override
    public void executePythonComputing(final OneDimensionArgumentsModel arguments,
                                       final String solutionFunctionFilePath,
                                       final String graphFilePath) {

        ProcessBuilder processBuilder = new ProcessBuilder(
                PYTHON_COMMAND,
                createFolderPythonScriptPath,
                arguments.getModelId(),
                arguments.getStateFunctionId(),
                arguments.getDimensionsNumberId(),
                arguments.getRegion(),
                arguments.getInitialObservation(),
                arguments.getLimitObservations(),
                arguments.getGeneralObservations(),
                solutionFunctionFilePath,
                graphFilePath
        );
        try {
            processBuilder.start();

            File solutionFunctionFile = new File(solutionFunctionFilePath);
            File graphFile = new File(graphFilePath);

            System.out.println(solutionFunctionFilePath);
            System.out.println(graphFilePath);

            while (!graphFile.exists() && !solutionFunctionFile.exists()) {
                Thread.sleep(500);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
