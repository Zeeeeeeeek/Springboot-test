package com.zhejianglab.spring3service.redis;

import java.text.MessageFormat;

/**
 * @author chenze
 * @date 2022/3/25
 */
public class RedisKeyUtil {

    private final static String USER_TOKEN = "zhejiang:spring3:token:{0}";

    private final static String USER_REFRESH_TOKEN = "zhejiang:spring3:refreshToken:{0}";

    private final static String USER_INFO = "zhejiang:spring3:user:{0}";

    public static String userTokenKey(String userId) {
        return MessageFormat.format(USER_TOKEN,userId);
    }

    public static String userRefreshTokenKey(String userId) {
        return MessageFormat.format(USER_REFRESH_TOKEN,userId);
    }
    public static String userInfo(String userId) {
        return MessageFormat.format(USER_INFO, userId);
    }

}
