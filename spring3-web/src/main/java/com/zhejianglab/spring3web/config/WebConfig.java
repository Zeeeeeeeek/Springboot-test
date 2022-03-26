package com.zhejianglab.spring3web.config;

import com.zhejianglab.spring3common.constant.Constants;
import com.zhejianglab.spring3web.interceptor.LoginInterceptor;
import com.zhejianglab.spring3web.interceptor.TimeConsumingInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author chenze
 * @date 2022/3/24
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                // 需拦截的URI配置
                .addPathPatterns("/**")
                // 不需拦截的URI配置
                .excludePathPatterns(Constants.INTERCEPTOR_EXCLUDE_PATH);
        registry.addInterceptor(new TimeConsumingInterceptor())
                // 需拦截的URI配置
                .addPathPatterns("/**")
                // 不需拦截的URI配置
                .excludePathPatterns(Constants.INTERCEPTOR_EXCLUDE_PATH);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 映射 webjars 资源
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}