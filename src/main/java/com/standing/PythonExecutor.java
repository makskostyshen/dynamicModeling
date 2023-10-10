package com.standing;

import io.micronaut.context.annotation.Property;
import jakarta.inject.Singleton;

import java.io.IOException;

@Singleton
public class PythonExecutor {
    private static final String PYTHON_COMMAND = "python";

    @Property(name = "app.modeling.python.create-folder.path")
    private String creteFolderPythonScriptPath;

    @Property(name = "app.modeling.solutions.path")
    private String solutionsPath;

    public void executeCreatingDirectory() {

        ProcessBuilder processBuilder = new ProcessBuilder(
                PYTHON_COMMAND,
                creteFolderPythonScriptPath,
                solutionsPath);

        try {
            processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
