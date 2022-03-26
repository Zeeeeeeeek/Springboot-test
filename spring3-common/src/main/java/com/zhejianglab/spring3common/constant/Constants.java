package com.zhejianglab.spring3common.constant;

/**
 * @author chenze
 * @date 2022/3/25
 */
public class Constants {

    public static final Integer USER_VALID = 1;

    public static final String[] SKIP_JWT_AUTHORIZATION_PATH = {"/auth/**", "/tool/**", "/test/**"};
    public static final String[] INTERCEPTOR_EXCLUDE_PATH = {"/swagger/**", "/static/**", "/resource/**"};
}
