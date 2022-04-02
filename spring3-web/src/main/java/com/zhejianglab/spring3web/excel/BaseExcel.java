package com.zhejianglab.spring3web.excel;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.zhejianglab.spring3common.dto.ResultCode;
import com.zhejianglab.spring3common.exception.CustomException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author chenze
 * @date 2022/4/1
 */
public abstract class BaseExcel<T> {


    /**
     * Excel导入
     *
     * @param inputStream
     * @return
     */
    public abstract List<T> excelImport(InputStream inputStream);

    /**
     * 普通excel导出
     */
    public abstract void normalExcelExport(List<T> list);

    /**
     * 大量excel导出
     */
    public abstract void bigExcelExport(List<T> list);

    /**
     * 输出excel文件
     */
    protected static void WriteResponse(ExcelWriter excelWriter, String filenames) {
        filenames = new String(filenames.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        getResponse().setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        getResponse().setHeader("Content-Disposition", "attachment;filename=" + filenames + ".xlsx");
        ServletOutputStream out;
        try {
            out = getResponse().getOutputStream();
        } catch (IOException e) {
            throw new CustomException(ResultCode.SYSTEM_INNER_ERROR, "获取OutputStream失败");
        }
        excelWriter.flush(out, true);
        excelWriter.close();
        IoUtil.close(out);
    }

    /**
     * 返回当前上下文的响应对象
     *
     * @return 返回当前上下文的响应对象
     */
    private static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 返回当前Servlet上下文的请求参数对象
     *
     * @return 返回当前Servlet上下文的请求参数对象
     */
    private static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes attributes) {
            return attributes;
        } else {
            throw new CustomException(ResultCode.SYSTEM_INNER_ERROR, "当前上下文环境非Servlet环境");
        }
    }
}
