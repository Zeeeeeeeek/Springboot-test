package com.zhejianglab.spring3web.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zhejianglab.spring3common.dto.Result;
import com.zhejianglab.spring3common.dto.ResultCode;
import com.zhejianglab.spring3common.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import static com.zhejianglab.spring3common.utils.JwtUtil.*;

/**
 * @author chenze
 * @date 2022/3/25
 */
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private static final PathMatcher pathmatcher = new AntPathMatcher();
    private String[] protectUrlPattern = {"/user/**", "/test/**"}; //哪  些请求需要进行安全校验

    public JwtAuthorizationFilter() {

    }


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (isProtectedUrl(httpServletRequest)) {
            String token = httpServletRequest.getHeader(HEADER_STRING);
            if (StrUtil.isBlank(token)) {
                log.error(ResultCode.PARAM_TOKEN_MISSING.getMessage());
                returnJsonData(httpServletResponse,ResultCode.PARAM_TOKEN_MISSING);
                return;
            }
            if(!JwtUtil.validate(token)){
                log.error(ResultCode.TOKEN_NO_ACCESS.getMessage());
                returnJsonData(httpServletResponse,ResultCode.TOKEN_NO_ACCESS);
                return;
            }
            Map<String, Object> claims = JwtUtil.getClaims(token);
            String role = String.valueOf(claims.get(ROLE));
            String userid = String.valueOf(claims.get(ID));
            //最关键的部分就是这里, 我们直接注入了
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    userid, null, List.of(() -> role)
            ));
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    //是否是保护连接
    private boolean isProtectedUrl(HttpServletRequest request) {

        boolean flag = false;
        for (int i = 0; i < protectUrlPattern.length; i++) {
            if (pathmatcher.match(protectUrlPattern[i], request.getServletPath())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回 json 格式的数据
     */
    private void returnJsonData(HttpServletResponse response, ResultCode resultCode) throws IOException {
        PrintWriter pw;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        pw = response.getWriter();
        pw.print(JSONUtil.toJsonStr(Result.failure(resultCode)));

    }
}