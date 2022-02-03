package com.moment.the.improvement.repository;

import com.moment.the.improvement.dto.ImprovementDto;

import java.util.List;
import java.util.Optional;

public interface ImprovementCustomRepository {
    List<ImprovementDto.Response> getAllImprovement();
    Optional<ImprovementDto.Response> findImprovementById(Long improveIdx);
}
