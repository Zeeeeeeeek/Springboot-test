package com.zhejianglab.spring3common.Annotation;

import java.lang.annotation.*;

/**
 * @author chenze
 * @date 2022/3/26
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface ApiOptions {
    /**
     * 是否校验请求头
     *
     * @return
     */
    boolean verify() default true;

    /**
     * 是否登录态访问.
     *
     * @return
     */
    boolean login() default true;

    /**
     * 是否根据响应格式封装.
     *
     * @return
     */
    boolean format() default true;
}