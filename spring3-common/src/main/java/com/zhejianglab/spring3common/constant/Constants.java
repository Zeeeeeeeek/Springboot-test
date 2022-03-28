package com.zhejianglab.spring3common.constant;

/**
 * @author chenze
 * @date 2022/3/25
 */
public class Constants {

    public static final Integer USER_VALID = 1;
    public static final Integer USER_INVALID = 2;

    public static final String ROLE_ADMIN = "'ADMIN'";
    public static final String ROLE_NORMAL = "'NORMAL'";
    public static final String ROLE_SUPPORT = "'SUPPORT'";

    public static final String[] SKIP_JWT_AUTHORIZATION_PATH = {"/auth/**", "/tool/**", "/test/**","/swagger-ui/**","/v3/**","/swagger-ui.html"};
    public static final String[] INTERCEPTOR_EXCLUDE_PATH = {"/swagger-ui/**", "/static/**", "/resource/**","/v3/**"};
}
