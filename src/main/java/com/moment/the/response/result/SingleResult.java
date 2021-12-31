package com.moment.the.response.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResult<T> extends CommonResult {
    @ApiModelProperty(value = "응답 데이터")
    private T data;
}
