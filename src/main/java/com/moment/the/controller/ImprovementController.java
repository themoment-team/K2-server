package com.moment.the.controller;

import com.moment.the.dto.ImprovementDto;
import com.moment.the.service.ImprovementService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ImprovementController {
    final private ImprovementService improvementService;
    // 개선사례작성
    @PostMapping("/admin/solved")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    // 개선사례작성
    public void save(@RequestBody ImprovementDto improvementDto){
        improvementService.create(improvementDto);
    }
    // 개선사례보기
    @GetMapping("/solved")
    public List<ImprovementDto> view(){
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
