package com.moment.the.controller.release;

import com.moment.the.domain.response.CommonResult;
import com.moment.the.domain.response.ResponseService;
import com.moment.the.dto.AnswerDto;
import com.moment.the.service.AnswerService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/top10")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
    private final ResponseService responseService;

    @PostMapping("/answer/{boardIdx}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult save(@RequestBody AnswerDto answerDto, @PathVariable Long boardIdx) throws Exception {
        answerService.save(answerDto, boardIdx);
        return responseService.getSuccessResult();
    }

    @PutMapping("/answer/{answerIdx}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult update(@RequestBody AnswerDto answerDto, @PathVariable Long answerIdx) throws Exception {
        answerService.update(answerDto, answerIdx);
        return responseService.getSuccessResult();
    }

    @GetMapping("/answer/{answerIdx}")
    public CommonResult view(@PathVariable Long answerIdx) throws Exception {
        answerService.read(answerIdx);
        return responseService.getSuccessResult();
    }

    @DeleteMapping("/answer/{answerIdx}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "RefreshToken", value = "로그인 성공 후 refresh_token", required = false, dataType = "String", paramType = "header")
    })
    public CommonResult delete(@PathVariable Long answerIdx) throws Exception {
        answerService.delete(answerIdx);
        return responseService.getSuccessResult();
    }
}
