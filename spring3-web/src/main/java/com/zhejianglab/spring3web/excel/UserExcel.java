package com.zhejianglab.spring3web.excel;

import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.zhejianglab.spring3dao.entity.User;

import java.io.InputStream;
import java.util.List;

/**
 * @author chenze
 * @date 2022/4/1
 */
public class UserExcel extends BaseExcel<User> {

    @Override
    public List<User> excelImport(InputStream inputStream) {
        ExcelReader excelReader = ExcelUtil.getReader(inputStream);
        return excelReader.readAll(User.class);
    }

    @Override
    public void normalExcelExport(List<User> list) {
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
        excelWriter.write(list, true);
        excelWriter.autoSizeColumnAll();
        WriteResponse(excelWriter, "用户" + System.currentTimeMillis());
    }

    @Override
    public void bigExcelExport(List<User> list) {
        BigExcelWriter excelWriter = ExcelUtil.getBigWriter();
        excelWriter.write(list, true);
        excelWriter.autoSizeColumnAll();
        WriteResponse(excelWriter, "用户" + System.currentTimeMillis());
    }
}
