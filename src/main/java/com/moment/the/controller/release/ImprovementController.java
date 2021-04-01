package com.moment.the.controller.release;

import com.moment.the.domain.response.CommonResult;
import com.moment.the.domain.response.ResponseService;
import com.moment.the.dto.ImprovementDto;
import com.moment.the.service.ImprovementService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ImprovementController {
    final private ImprovementService improvementService;
    private final ResponseService responseService;

    // 개선사례작성
    @PostMapping("/solved")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult save(@Valid @RequestBody ImprovementDto improvementDto){
        improvementService.create(improvementDto);
        return responseService.getSuccessResult();
    }

    // 개선사례보기
    @GetMapping("/solved")
    public List<ImprovementDto> view(){
        return improvementService.read();
    }

    // 개선사례수정
    @PutMapping("/solved/{improveIdx}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult update(@RequestBody ImprovementDto improvementDto, @PathVariable Long improveIdx) {
        improvementService.update(improvementDto, improveIdx);
        return responseService.getSuccessResult();
    }

    // 개선사례삭제
    @DeleteMapping("/solved/{improveIdx}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult delete(@PathVariable Long improveIdx) {
        improvementService.delete(improveIdx);
        return responseService.getSuccessResult();
    }
}
