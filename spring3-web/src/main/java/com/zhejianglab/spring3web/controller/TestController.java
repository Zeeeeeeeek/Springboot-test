package com.zhejianglab.spring3web.controller;

import com.zhejianglab.spring3common.dto.Result;
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
    public Result hello() {
        return Result.success("你好");
    }

    @GetMapping("set")
    public Result set() {
        redisUtil.set("kk:test:1","ssss",60, TimeUnit.SECONDS);
        return Result.success("你好");
    }

    @GetMapping("get")
    public Result get() {
        return Result.success(redisUtil.get("kk:test:1"));
    }

}
