package com.zhejianglab.spring3web.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.zhejianglab.spring3common.dto.Result;
import com.zhejianglab.spring3common.dto.ResultCode;
import com.zhejianglab.spring3common.eunm.RoleEnums;
import com.zhejianglab.spring3common.utils.JwtUtil;
import com.zhejianglab.spring3dao.dto.RefreshTokenBean;
import com.zhejianglab.spring3dao.dto.UserDTO;
import com.zhejianglab.spring3dao.entity.User;
import com.zhejianglab.spring3dao.vo.JwtVo;
import com.zhejianglab.spring3dao.vo.UserVo;
import com.zhejianglab.spring3service.redis.RedisKeyUtil;
import com.zhejianglab.spring3service.redis.RedisUtil;
import com.zhejianglab.spring3service.service.IUserService;
import com.zhejianglab.spring3web.exception.CustomException;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.zhejianglab.spring3common.utils.JwtUtil.*;

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
        String userKey = RedisKeyUtil.userInfo(String.valueOf(user.getId()));
        UserVo userVo = new UserVo();
        BeanUtil.copyProperties(user,userVo);
        redisUtil.set(userKey,userVo);
        return Result.success(generateJwt(key,refreshKey,user));
    }

    @PostMapping("/refresh")
    @ResponseBody
    public Result refresh(@RequestBody RefreshTokenBean refreshTokenBean)  {
        if(JwtUtil.validate(refreshTokenBean.getRefreshToken())){
            Map<String,Object> map = JwtUtil.getClaims(refreshTokenBean.getRefreshToken());
            String userId = (String) map.get(ID);
            String role = (String) map.get(ROLE);
            String key = RedisKeyUtil.userTokenKey(userId);
            String refreshKey = RedisKeyUtil.userRefreshTokenKey(userId);
            String current_refreshToken = (String) redisUtil.get(refreshKey);
            if(!current_refreshToken.equals(refreshTokenBean.getRefreshToken())){
                return Result.failure(ResultCode.TOKEN_NO_ACCESS,"refreshToken已失效");
            }
            JwtVo jwtVo = new JwtVo();
            Date current = new Date();
            String newToken = JwtUtil.generateToken(role,userId,current);
            String newRefreshToken = JwtUtil.generateRefreshToken(role,userId,current);
            redisUtil.set(key,newToken,EXPIRATION_TIME/1000, TimeUnit.SECONDS);
            redisUtil.set(refreshKey,newRefreshToken,REFRESH_TIME/1000, TimeUnit.SECONDS);
            jwtVo.setJwtToken(newToken);
            jwtVo.setRefreshToken(newRefreshToken);
            jwtVo.setExpiration(String.valueOf(EXPIRATION_TIME/1000));
            return Result.success(jwtVo);
        }
        return Result.failure(ResultCode.TOKEN_NO_ACCESS,"已超过最大续期时间");
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
