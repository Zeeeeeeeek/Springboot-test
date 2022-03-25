package com.zhejianglab.spring3web.interceptor;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * @author chenze
 * @date 2022/3/24
 */
@Slf4j
public class TimeConsumingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 记录请求开始时间
        request.setAttribute("_startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求结束时间
        Long endTime = System.currentTimeMillis();
        // 从HttpServletRequest获取开始时间
        Long startTime = (Long) request.getAttribute("_startTime");
        // 打印接口信息及耗时
        log.info("RequestIP: {} URL: {}；Consume：{}ms", request.getRemoteAddr(), getFullUrl(request), ((endTime - startTime) * 1.000));
    }

    /**
     * 获取完整的URL路径
     *
     * @param request 请求对象{@link HttpServletRequest}
     * @return 完整的URL路径
     */
    private String getFullUrl(HttpServletRequest request) {
        //记录请求参数
        StringBuilder sb = new StringBuilder();
        String method = request.getMethod();
        sb.append(method).append(" ");
        sb.append(request.getRequestURL().toString());
        if (RequestMethod.POST.name().equals(method)) {
            //获取参数
            Map<String, String[]> pm = request.getParameterMap();
            Set<Map.Entry<String, String[]>> es = pm.entrySet();
            Iterator<Map.Entry<String, String[]>> iterator = es.iterator();
            int pointer = 0;
            while (iterator.hasNext()) {
                if (pointer == 0) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                Map.Entry<String, String[]> next = iterator.next();
                String key = next.getKey();
                String[] value = next.getValue();
                for (int i = 0; i < value.length; i++) {
                    if (i != 0) {
                        sb.append("&");
                    }
                    if (value[i].length() <= 20) {
                        sb.append(key).append("=").append(value[i]);
                    } else {
                        sb.append(key).append("=").append(StrUtil.subPre(value[i], 20)).append("…");
                    }
                }
                pointer++;
            }
        }
        return sb.toString();
    }
}
