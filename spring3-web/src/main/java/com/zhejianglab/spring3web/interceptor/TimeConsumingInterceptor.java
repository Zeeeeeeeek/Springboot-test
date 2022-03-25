package com.zhejianglab.spring3web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * @author chenze
 * @date 2022/3/24
 */
@Slf4j
public class TimeConsumingInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 请求结束时间
        Long endTime = System.currentTimeMillis();
        // 从HttpServletRequest获取开始时间
        Long startTime = (Long) request.getAttribute("_startTime");
        // 打印接口信息及耗时
        log.info("RequestId: {} URL: {}；Consume：{}ms", request.getAttribute("_UUID"), request.getRequestURL().toString(), ((endTime - startTime) * 1.000));
    }
}
