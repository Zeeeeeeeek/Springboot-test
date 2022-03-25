package com.zhejianglab.spring3web.controller;

import cn.hutool.crypto.SecureUtil;
import com.zhejianglab.spring3common.dto.Result;
import com.zhejianglab.spring3common.dto.ResultCode;
import com.zhejianglab.spring3common.eunm.RoleEnums;
import com.zhejianglab.spring3common.utils.JwtUtil;
import com.zhejianglab.spring3dao.dto.UserDTO;
import com.zhejianglab.spring3dao.entity.User;
import com.zhejianglab.spring3service.service.IUserService;
import com.zhejianglab.spring3web.exception.CustomException;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenze
 * @date 2022/3/25
 */
@RestController
@RequestMapping("auth")
public class AuthController {

    @Resource
    private IUserService userService;

    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestBody @Valid UserDTO userDTO)  {
        User user = this.userService.findByUserNameAndPass(userDTO.getUserName(), SecureUtil.md5(userDTO.getPassword()));
        if(null == user){
            throw new CustomException(ResultCode.USER_LOGIN_ERROR);
        }
        return Result.success(JwtUtil.generateToken(RoleEnums.parse(user.getRoleType()).toString(),String.valueOf(user.getId())));
    }
}
