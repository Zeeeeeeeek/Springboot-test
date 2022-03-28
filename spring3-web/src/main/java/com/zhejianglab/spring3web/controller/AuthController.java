package com.zhejianglab.spring3web.controller;

import cn.hutool.crypto.SecureUtil;
import com.zhejianglab.spring3common.Annotation.ApiOptions;
import com.zhejianglab.spring3common.dto.Result;
import com.zhejianglab.spring3common.dto.ResultCode;
import com.zhejianglab.spring3common.utils.JwtUtil;
import com.zhejianglab.spring3dao.dto.RefreshDTO;
import com.zhejianglab.spring3dao.dto.UserDTO;
import com.zhejianglab.spring3service.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 登录及token续期
 *
 * @author chenze
 * @date 2022/3/25
 */
@Tag(name = "登录控制器", description = "登录及Token续期")
@RestController
@RequestMapping("auth")
public class AuthController {

    @Resource
    private IUserService userService;

    @PostMapping("/login")
    @ApiOptions(login = false)
    @Operation(description = "登录")
    @ResponseBody
    public Result login(@RequestBody @Valid UserDTO userDTO) {
        userDTO.setPassword(SecureUtil.md5(userDTO.getPassword()));
        return Result.success(this.userService.login(userDTO));
    }

    @PostMapping("/refresh")
    @ApiOptions(login = false)
    @Operation(description = "token续期")
    @ResponseBody
    public Result refresh(@RequestBody RefreshDTO refreshTokenBean) {
        if (JwtUtil.validate(refreshTokenBean.getRefreshToken())) {
            return Result.success(this.userService.refresh(refreshTokenBean));
        }
        return Result.failure(ResultCode.TOKEN_NO_ACCESS, "已超过最大续期时间");
    }
}
