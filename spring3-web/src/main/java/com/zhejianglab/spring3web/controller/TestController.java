package com.zhejianglab.spring3web.controller;

import com.zhejianglab.spring3common.dto.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenze
 * @date 2022/3/23
 */
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("hello")
    public Result hello() {
        return Result.success("你好");
    }
}
