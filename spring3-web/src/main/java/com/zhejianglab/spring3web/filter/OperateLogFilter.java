package com.zhejianglab.spring3web.filter;

import com.alibaba.fastjson.JSONObject;
import com.zhejianglab.spring3common.constant.Constants;
import com.zhejianglab.spring3common.utils.Sequence;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author chenze
 * @date 2022/3/25
 */
@Component
@Slf4j
public class OperateLogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            // 记录请求开始时间
            servletRequest.setAttribute("_startTime", System.currentTimeMillis());
            int requestId = Sequence.nextValue();
            servletRequest.setAttribute("_UUID", requestId);
            String requestBody;
            if (servletRequest.getContentType().startsWith(Constants.MULTIPART_CONTENT_TYPE)) {
                //在遇到 multipart/form-data时放行
                requestBody = Constants.MULTIPART_CONTENT_TYPE_BODY;
                log.info("RequestId: {} URL: {} Body:{}", requestId, ((HttpServletRequest) servletRequest).getRequestURL().toString(), requestBody);
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
                if ("GET".equals(requestWrapper.getMethod())) {
                    requestBody = JSONObject.toJSONString(requestWrapper.getParameterMap());
                } else {
                    requestBody = requestWrapper.getBody();
                }
                requestBody = requestBody.replaceAll("\r\n", "");
                requestBody = requestBody.replaceAll(" {2}", " ");
                log.info("RequestId: {} URL: {} Body:{}", requestId, ((HttpServletRequest) servletRequest).getRequestURL().toString(), requestBody);
                filterChain.doFilter(requestWrapper, servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
