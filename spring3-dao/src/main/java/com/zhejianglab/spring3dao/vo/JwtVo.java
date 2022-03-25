package com.zhejianglab.spring3dao.vo;

import lombok.Data;

/**
 * @author chenze
 * @date 2022/3/25
 */
@Data
public class JwtVo {
    private String jwtToken;
    private String refreshToken;
    private String expiration;
}
