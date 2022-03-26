package com.zhejianglab.spring3web.controller;

import cn.hutool.crypto.SecureUtil;
import com.zhejianglab.spring3common.Annotation.ApiOptions;
import com.zhejianglab.spring3common.dto.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenze
 * @date 2022/3/24
 */
@RestController
@RequestMapping("tool")
public class ToolController {

    @GetMapping("enpt")
    @ApiOptions(login = false)
    public Result enpt(String password) {
        return Result.success(SecureUtil.md5(password));
    }
}
