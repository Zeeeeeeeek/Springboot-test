package com.zhejianglab.spring3web.handler;

import cn.hutool.json.JSONUtil;
import com.zhejianglab.spring3common.dto.Result;
import com.zhejianglab.spring3common.dto.ResultCode;
import com.zhejianglab.spring3common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenze
 * @since 2022/3/24
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public Result handleCustomException(CustomException ex) {
        log.error("自定义异常: {}", ex.getMessage());
        return Result.failure(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public Result handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        log.error("字段唯一性报错: {}", ex.getMessage());
        return Result.failure(ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String defaultMessage = error.getDefaultMessage();
            errors.put(field, defaultMessage);
        });
        log.error("参数校验出错: {}", JSONUtil.toJsonStr(errors));
        return Result.failure(ResultCode.PARAM_VALIDATE_ERROR, errors);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public Result handleAccessDeniedException(AccessDeniedException ex) {
        log.error("权限校验出错: {}", ex.getMessage());
        return Result.failure(ResultCode.PERMISSION_NO_ACCESS);
    }


}
