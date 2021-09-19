package com.moment.the.improvement.controller;

import com.moment.the.improvement.dto.ImprovementDto;
import com.moment.the.improvement.dto.ImprovementViewAllDto;
import com.moment.the.improvement.service.ImprovementService;
import com.moment.the.response.ResponseService;
import com.moment.the.response.result.CommonResult;
import com.moment.the.response.result.ListResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ImprovementController {
    private final ImprovementService improvementService;
    private final ResponseService responseService;

    // 개선사례작성
    @PostMapping("/solved")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult createThisImprovement(@Valid @RequestBody ImprovementDto improvementDto){
        improvementService.createThisImprovement(improvementDto);
        return responseService.getSuccessResult();
    }

    // 개선사례보기
    @GetMapping("/solved")
    public ListResult<ImprovementViewAllDto> getThisImprovement(){
        return responseService.getListResult(improvementService.getThisImprovement());
    }

    // 개선사례수정
    @PutMapping("/solved/{improveIdx}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult updateThisImprovement(@RequestBody ImprovementDto improvementDto, @PathVariable Long improveIdx) {
        improvementService.updateThisImprovement(improvementDto, improveIdx);
        return responseService.getSuccessResult();
    }

    // 개선사례삭제
    @DeleteMapping("/solved/{improveIdx}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult deleteThisImprovement(@PathVariable Long improveIdx) {
        improvementService.deleteThisImprovement(improveIdx);
        return responseService.getSuccessResult();
    }
}
