package com.zhejianglab.spring3web.controller;

import cn.hutool.crypto.SecureUtil;
import com.zhejianglab.spring3common.Annotation.ApiOptions;
import com.zhejianglab.spring3common.dto.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenze
 * @date 2022/3/24
 */
@RestController
@Tag(name = "工具类控制器", description = "工具类操作")
@RequestMapping("tool")
public class ToolController {

    @GetMapping("enpt")
    @Operation(description = "MD5加密")
    @ApiOptions(login = false)
    public Result enpt(String password) {
        return Result.success(SecureUtil.md5(password));
    }
}
