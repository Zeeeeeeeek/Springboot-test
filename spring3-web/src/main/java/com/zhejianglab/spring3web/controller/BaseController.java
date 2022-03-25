package com.zhejianglab.spring3web.controller;

import com.zhejianglab.spring3common.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.zhejianglab.spring3common.utils.JwtUtil.HEADER_STRING;
import static com.zhejianglab.spring3common.utils.JwtUtil.ID;


public abstract class BaseController {

    /**
     * 返回当前上下文的请求对象
     *
     * @return 返回当前上下文的请求对象
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 返回token
     *
     * @return 返回token
     */
    public static String getToken(){
        // 获取 token（从 header 或者 参数中获取）
        String token = getRequest().getHeader(HEADER_STRING);
        return token;
    }

    /**
     * 返回jwtData
     *
     * @return 返回JwtData
     */
    public static String getUserId(){
        return (String) JwtUtil.getClaims(getToken()).get(ID);
    }


    /**
     * 返回当前上下文的响应对象
     *
     * @return 返回当前上下文的响应对象
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes attributes) {
            return attributes;
        } else {
            throw new IllegalStateException("当前上下文环境非Servlet环境");
        }
    }
}

