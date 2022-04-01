package com.zhejianglab.spring3common.constant;

/**
 * @author chenze
 * @date 2022/3/25
 */
public class Constants {

    public static final Integer USER_VALID = 1;
    public static final Integer USER_INVALID = 2;

    public static final Integer STATUS_VALID = 1;
    public static final Integer STATUS_INVALID = 0;

    /**
     * 单次执行
     */
    public static final Integer TASK_EXECUTE_TYPE_ONE_TIME = 0;

    /**
     * 循环执行
     */
    public static final Integer TASK_EXECUTE_TYPE_CRON = 1;

    public static final String ROLE_ADMIN = "'ADMIN'";
    public static final String ROLE_NORMAL = "'NORMAL'";
    public static final String ROLE_SUPPORT = "'SUPPORT'";

    public static final String MULTIPART_CONTENT_TYPE = "multipart/form-data";
    public static final String MULTIPART_CONTENT_TYPE_BODY = "file upload";

    public static final String[] SKIP_JWT_AUTHORIZATION_PATH = {"/auth/**", "/tool/**", "/test/**", "/swagger-ui/**", "/v3/**", "/swagger-ui.html"};
    public static final String[] INTERCEPTOR_EXCLUDE_PATH = {"/swagger-ui/**", "/static/**", "/resource/**", "/v3/**"};
}
