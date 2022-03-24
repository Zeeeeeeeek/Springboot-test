package com.zhejianglab.spring3web.exception;

import com.alibaba.fastjson.JSON;
import com.zhejianglab.spring3common.dto.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;

/**
 * @author chenze
 * @since 2022/3/24
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    @ResponseBody
    public void customerException(HttpServletRequest req, HttpServletResponse res, CustomException ex) throws Exception {
        log.error("自定义异常:" + ex);
        respData(res, Result.failure(ex.getCode(), ex.getMessage()));
    }

    private void respData(HttpServletResponse res, Object data) throws Exception {
        res.setContentType("application/json;charset=utf-8");
        res.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter printWriter = res.getWriter();
        printWriter.write(JSON.toJSONString(data));
        printWriter.flush();
        printWriter.close();
    }
}
