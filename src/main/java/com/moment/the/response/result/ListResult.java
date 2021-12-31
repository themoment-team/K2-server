package com.moment.the.response.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListResult<T> extends CommonResult {
    @ApiModelProperty(value = "응답 데이터")
    private List<T> list;
}