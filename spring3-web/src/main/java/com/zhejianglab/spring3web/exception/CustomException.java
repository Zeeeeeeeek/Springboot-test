package com.zhejianglab.spring3web.exception;

import com.zhejianglab.spring3common.dto.ResultCode;
import lombok.Getter;

import java.io.Serial;

/**
 * @author shark
 * @since 2021/11/23
 */
public class CustomException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private final Integer code;
    @Getter
    private final String message;


    public CustomException(ResultCode resultCode) {
        code = resultCode.getCode();
        message = resultCode.getMessage();
    }

    public CustomException(ResultCode resultCode, String message) {
        this.code = resultCode.getCode();
        this.message = message;
    }

    public CustomException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
