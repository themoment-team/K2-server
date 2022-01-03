package com.moment.the.improvement.repository;

import com.moment.the.improvement.dto.ImprovementDto;

import java.util.List;

public interface ImprovementCustomRepository {
    List<ImprovementDto.Response> getAllImprovement();
    ImprovementDto.Response findImprovementById(Long improveIdx);
}
