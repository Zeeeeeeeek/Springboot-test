package com.zhejianglab.spring3common.utils;

import cn.hutool.jwt.JWT;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenze
 * @date 2022/3/25
 */
@Slf4j
public class JwtUtil {
    public static final long EXPIRATION_TIME = 15 * 60 * 1000L;// 令牌环有效期 15 min
    public static final long REFRESH_TIME = 60 * 60 * 1000L;// 续期60 min
    public static final byte[] SECRET = "spring3test".getBytes(StandardCharsets.UTF_8);//令牌环密钥
    public static final String TOKEN_PREFIX = "Bearer";//令牌环头标识
    public static final String HEADER_STRING = "Authorization";//配置令牌环在http heads中的键值
    public static final String ROLE = "ROLE";//自定义字段-角色字段
    public static final String ID = "userId";//自定义字段-角色字段


    /**
     * 生成JWT Token
     *
     * @param userRole
     * @param userid
     * @param date
     * @return
     */
    public static String generateToken(String userRole, String userid, Date date) {
        return getJwtString(userRole, userid, date, EXPIRATION_TIME);
    }

    /**
     * 生成Refresh Token
     *
     * @param userRole
     * @param userid
     * @param date
     * @return
     */
    public static String generateRefreshToken(String userRole, String userid, Date date) {
        return getJwtString(userRole, userid, date, REFRESH_TIME);
    }

    /**
     * 令牌环校验
     *
     * @param token
     * @return
     */
    public static Boolean validate(String token) {
        return JWT.of(token.replace(TOKEN_PREFIX + " ", "")).setKey(SECRET).verify();
    }

    /**
     * 获取body
     *
     * @param token
     * @return
     */
    public static Map<String, Object> getClaims(String token) {
        return JWT.of(token.replace(TOKEN_PREFIX + " ", "")).setKey(SECRET).getPayloads();
    }

    private static String getJwtString(String userRole, String userid, Date date, long refreshTime) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(ROLE, userRole);
        map.put(ID, userid);
        String jwt = JWT.create()
                .addPayloads(map)
                .setIssuedAt(date)
                .setExpiresAt(new Date(date.getTime() + refreshTime))
                .setKey(SECRET)
                .sign();
        return TOKEN_PREFIX + " " + jwt;
    }

}
