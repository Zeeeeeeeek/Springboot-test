package com.zhejianglab.spring3web.config;

import com.zhejianglab.spring3web.interceptor.TimeConsumingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author chenze
 * @date 2022/3/24
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(false)
                .maxAge(86400);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TimeConsumingInterceptor())
                // 需拦截的URI配置
                .addPathPatterns("/**")
                // 不需拦截的URI配置
                .excludePathPatterns("/swagger/**", "/static/**", "/resource/**");
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