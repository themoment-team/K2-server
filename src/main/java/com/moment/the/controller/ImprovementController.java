package com.moment.the.controller;

import com.moment.the.domain.ImprovementDomain;
import com.moment.the.dto.ImprovementDto;
import com.moment.the.service.ImprovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ImprovementController {
    final private ImprovementService improvementService;
    // 개선사례작성
    @PostMapping("/solved")
    public void save(@RequestBody ImprovementDto improvementDto){
        improvementService.create(improvementDto);
    }
    // 개선사례보기
    @GetMapping("/solved")
    public List<ImprovementDomain> view(){
        return improvementService.read();
    }
    // 개선사례수정
    @PutMapping("/solved/{improveIdx}")
    public void update(@RequestBody ImprovementDto improvementDto, @PathVariable Long improveIdx) throws Exception {
        improvementService.update(improvementDto, improveIdx);
    }
    // 개선사례삭제
    @DeleteMapping("/solved/{improveIdx}")
    public void delete(@PathVariable Long improveIdx) throws Exception {
        improvementService.delete(improveIdx);
    }
}