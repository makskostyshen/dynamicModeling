package com.standing.service.api;

import com.standing.service.model.OneDimensionArgumentsModel;

public interface CommandsExecutor {
    void executePythonComputing(OneDimensionArgumentsModel arguments,
                                String solutionFunctionFilePath,
                                String graphFilePath);

    void createFolder(final String folderName);
}
