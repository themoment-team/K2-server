package com.moment.the.answer.controller;


import com.moment.the.answer.dto.AnswerDto;
import com.moment.the.answer.dto.AnswerResDto;
import com.moment.the.answer.service.AnswerService;
import com.moment.the.response.ResponseService;
import com.moment.the.response.result.CommonResult;
import com.moment.the.response.result.SingleResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/rank")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
    private final ResponseService responseService;

    @PostMapping("/answer/{uncomfortableIdx}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult createThisAnswer(@RequestBody AnswerDto answerDto, @PathVariable Long uncomfortableIdx) {
        answerService.createThisAnswer(answerDto, uncomfortableIdx);
        return responseService.getSuccessResult();
    }

    @PutMapping("/answer/{answerIdx}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult updateThisAnswer(@RequestBody AnswerDto answerDto, @PathVariable Long answerIdx) {
        answerService.updateThisAnswer(answerDto, answerIdx);
        return responseService.getSuccessResult();
    }

    @GetMapping("/answer/{uncomfortableIdx}")
    public SingleResult<AnswerResDto> getThisAnswer(@PathVariable Long uncomfortableIdx) {
        return responseService.getSingleResult(answerService.getThisAnswer(uncomfortableIdx));
    }

    @DeleteMapping("/answer/{answerIdx}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult deleteThisAnswer(@PathVariable Long answerIdx) {
        answerService.deleteThisAnswer(answerIdx);
        return responseService.getSuccessResult();
    }
}
