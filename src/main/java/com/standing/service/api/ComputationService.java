package com.standing.service.api;

import com.standing.dto.ResultDto;
import com.standing.service.model.OneDimensionArgumentsModel;

public interface ComputationService {
    ResultDto compute(OneDimensionArgumentsModel arguments);
}
