package com.moment.the.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class SampleController {
    @RequestMapping(method = RequestMethod.GET, value = "sample",
            produces = MediaType.TEXT_PLAIN_VALUE)
    @ApiOperation(value = "Test Sample", tags = "sample")
    public ResponseEntity sample(@RequestParam String param) {
        return ResponseEntity.ok(param);
    }
}
