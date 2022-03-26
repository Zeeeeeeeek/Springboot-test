package com.zhejianglab.spring3web.interceptor;

import com.zhejianglab.spring3common.Annotation.ApiOptions;
import com.zhejianglab.spring3common.dto.ResultCode;
import com.zhejianglab.spring3common.exception.CustomException;
import com.zhejianglab.spring3dao.vo.UserVo;
import com.zhejianglab.spring3service.holder.SessionLocal;
import com.zhejianglab.spring3service.redis.RedisKeyUtil;
import com.zhejianglab.spring3service.redis.RedisUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author chenze
 * @date 2022/3/25
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisUtil redisUtil;

    /**
     * 根据注解配置选择是否需要登录
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ApiOptions mgrOptions = handlerMethod.getMethodAnnotation(ApiOptions.class);
            if (mgrOptions == null) {
                return true;
            }
            if (mgrOptions.login()) {
                checkLogin();
            }
            return true;
        } else {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
    }

    private void checkLogin() {
        String userId = String.valueOf(SessionLocal.getUserInfo().getId());
        if (!redisUtil.hasKey(RedisKeyUtil.userTokenKey(userId))) {
            throw new CustomException(ResultCode.USER_NOT_LOGGED_IN);
        }
        String cachedToken = (String) redisUtil.get(RedisKeyUtil.userTokenKey(userId));
        if (!cachedToken.equals(SessionLocal.getToken())) {
            throw new CustomException(ResultCode.TOKEN_NO_ACCESS, "token已失效,请重新登录");
        }
        String userKey = RedisKeyUtil.userInfo(userId);
        UserVo userVo = (UserVo) redisUtil.get(userKey);
        SessionLocal.setUserInfo(userVo);
    }
}
