package com.zhejianglab.spring3web.controller;

import com.zhejianglab.spring3common.Annotation.ApiOptions;
import com.zhejianglab.spring3common.dto.Result;
import com.zhejianglab.spring3common.dto.ResultCode;
import com.zhejianglab.spring3common.exception.CustomException;
import com.zhejianglab.spring3service.redis.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author chenze
 * @date 2022/3/23
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Resource
    private RedisUtil redisUtil;

    @GetMapping("hello")
    @ApiOptions(login = false)
    public Result hello() {
        return Result.success("你好");
    }

    @GetMapping("set")
    @ApiOptions(login = false)
    public Result set() {
        redisUtil.set("kk:test:1", "ssss", 60, TimeUnit.SECONDS);
        return Result.success("你好");
    }

    @GetMapping("get")
    @ApiOptions(login = false)
    public Result get() {
        return Result.success(redisUtil.get("kk:test:1"));
    }

    @GetMapping("error")
    @ApiOptions(login = false)
    public Result error(Integer a) {
        if (a > 1) {
            throw new CustomException(ResultCode.INTERFACE_FORBID_VISIT);
        }
        return Result.success();
    }

}
