package com.zhejianglab.spring3web.interceptor;

import com.zhejianglab.spring3common.dto.ResultCode;
import com.zhejianglab.spring3common.utils.JwtUtil;
import com.zhejianglab.spring3service.redis.RedisKeyUtil;
import com.zhejianglab.spring3service.redis.RedisUtil;
import com.zhejianglab.spring3web.exception.CustomException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

import static com.zhejianglab.spring3common.utils.JwtUtil.HEADER_STRING;
import static com.zhejianglab.spring3common.utils.JwtUtil.ID;

/**
 * @author chenze
 * @date 2022/3/25
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> claims = JwtUtil.getClaims(request.getHeader(HEADER_STRING));
        String userId = String.valueOf(claims.get(ID));
        if(!redisUtil.hasKey(RedisKeyUtil.userTokenKey(userId))){
            throw new CustomException(ResultCode.USER_NOT_LOGGED_IN);
        }
        String current_refreshToken = (String) redisUtil.get(RedisKeyUtil.userTokenKey(userId));
        if(!current_refreshToken.equals(request.getHeader(HEADER_STRING))){
            throw new CustomException(ResultCode.TOKEN_NO_ACCESS,"token已失效,请重新登录");
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}