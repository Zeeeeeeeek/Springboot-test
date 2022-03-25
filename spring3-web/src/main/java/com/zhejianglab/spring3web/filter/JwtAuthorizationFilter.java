package com.zhejianglab.spring3web.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zhejianglab.spring3common.dto.Result;
import com.zhejianglab.spring3common.dto.ResultCode;
import com.zhejianglab.spring3common.utils.JwtUtil;
import com.zhejianglab.spring3dao.vo.UserVo;
import com.zhejianglab.spring3service.holder.LocalInfo;
import com.zhejianglab.spring3service.holder.SessionLocal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String token = httpServletRequest.getHeader(HEADER_STRING);
        if (StrUtil.isBlank(token)) {
            log.error(ResultCode.PARAM_TOKEN_MISSING.getMessage());
            returnJsonData(httpServletResponse, ResultCode.PARAM_TOKEN_MISSING);
            return;
        }
        if (!JwtUtil.validate(token)) {
            log.error(ResultCode.TOKEN_NO_ACCESS.getMessage());
            returnJsonData(httpServletResponse, ResultCode.TOKEN_NO_ACCESS);
            return;
        }
        Map<String, Object> claims = JwtUtil.getClaims(token);
        String role = String.valueOf(claims.get(ROLE));
        String userid = String.valueOf(claims.get(ID));
        //注入角色权限信息
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                userid, null, List.of(() -> role)
        ));

        //放入ThreadLocal
        LocalInfo localInfo = new LocalInfo();
        UserVo userVo = new UserVo();
        userVo.setUserId(Long.valueOf(userid));
        localInfo.setToken(token);
        SessionLocal.setLocalInfo(localInfo);
        SessionLocal.setUserInfo(userVo);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
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