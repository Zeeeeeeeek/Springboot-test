package com.zhejianglab.spring3web.controller;

import cn.hutool.crypto.SecureUtil;
import com.zhejianglab.spring3common.dto.Result;
import com.zhejianglab.spring3common.dto.ResultCode;
import com.zhejianglab.spring3common.eunm.RoleEnums;
import com.zhejianglab.spring3common.utils.JwtUtil;
import com.zhejianglab.spring3dao.dto.UserDTO;
import com.zhejianglab.spring3dao.entity.User;
import com.zhejianglab.spring3dao.vo.JwtVo;
import com.zhejianglab.spring3service.redis.RedisKeyUtil;
import com.zhejianglab.spring3service.redis.RedisUtil;
import com.zhejianglab.spring3service.service.IUserService;
import com.zhejianglab.spring3web.exception.CustomException;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.zhejianglab.spring3common.utils.JwtUtil.EXPIRATION_TIME;
import static com.zhejianglab.spring3common.utils.JwtUtil.REFRESH_TIME;

/**
 * @author chenze
 * @date 2022/3/25
 */
@RestController
@RequestMapping("auth")
public class AuthController {

    @Resource
    private IUserService userService;

    @Resource
    private RedisUtil redisUtil;

    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestBody @Valid UserDTO userDTO)  {
        User user = this.userService.findByUserNameAndPass(userDTO.getUserName(), SecureUtil.md5(userDTO.getPassword()));
        if(null == user){
            throw new CustomException(ResultCode.USER_LOGIN_ERROR);
        }
        String key = RedisKeyUtil.userTokenKey(String.valueOf(user.getId()));
        String refreshKey = RedisKeyUtil.userRefreshTokenKey(String.valueOf(user.getId()));
        if(redisUtil.hasKey(key)){
          return Result.success(checkLogin(key,refreshKey));
        }

        return Result.success(generateJwt(key,refreshKey,user));
    }

    private JwtVo checkLogin(String tokenKey, String refreshTokenKey){
        JwtVo jwtVo = new JwtVo();
        String token = (String) redisUtil.get(tokenKey);
        String refreshToken = (String) redisUtil.get(refreshTokenKey);
        String expire = String.valueOf(redisUtil.getExpire(tokenKey));
        jwtVo.setJwtToken(token);
        jwtVo.setRefreshToken(refreshToken);
        jwtVo.setExpiration(expire);
        return jwtVo;
    }

    private JwtVo generateJwt(String tokenKey, String refreshTokenKey, User user){
        JwtVo jwtVo = new JwtVo();
        Date current = new Date();
        String newToken = JwtUtil.generateToken(RoleEnums.parse(user.getRoleType()).toString(),String.valueOf(user.getId()),current);
        String newRefreshToken = JwtUtil.generateRefreshToken(RoleEnums.parse(user.getRoleType()).toString(),String.valueOf(user.getId()),current);
        redisUtil.set(tokenKey,newToken,EXPIRATION_TIME/1000, TimeUnit.SECONDS);
        redisUtil.set(refreshTokenKey,newRefreshToken,REFRESH_TIME/1000, TimeUnit.SECONDS);
        jwtVo.setJwtToken(newToken);
        jwtVo.setRefreshToken(newRefreshToken);
        jwtVo.setExpiration(String.valueOf(EXPIRATION_TIME/1000));
        return jwtVo;
    }
}
