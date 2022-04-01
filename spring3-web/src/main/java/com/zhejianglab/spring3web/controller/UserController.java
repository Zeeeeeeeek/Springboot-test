package com.zhejianglab.spring3web.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhejianglab.spring3common.Annotation.ApiOptions;
import com.zhejianglab.spring3common.constant.Constants;
import com.zhejianglab.spring3common.dto.DataList;
import com.zhejianglab.spring3common.dto.Result;
import com.zhejianglab.spring3common.dto.ResultCode;
import com.zhejianglab.spring3dao.dto.IdDTO;
import com.zhejianglab.spring3dao.entity.User;
import com.zhejianglab.spring3service.service.IUserService;
import com.zhejianglab.spring3web.excel.UserExcel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author chenze
 * @since 2022-03-24
 */
@RestController
@Tag(name = "用户控制器", description = "用户相关操作")
@RequestMapping("/user")
public class UserController extends UserExcel {

    @Resource
    private IUserService userService;

    @GetMapping("listByPage")
    @Operation(description = "列表查询")
    @ApiOptions
    public Result listByPage(@RequestParam(required = false, defaultValue = "1") Integer current,
                             @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
        Page<User> page = this.userService.selectByPage(current, pageSize);
        return Result.success(new DataList<>(page.getTotal(), page.getRecords()));
    }

    @PostMapping("save")
    @Operation(description = "保存更新用户")
    @ApiOptions
    public Result save(@RequestBody @Valid User user) {
        user.setPassword(SecureUtil.md5(user.getPassword()));
        return Result.success(this.userService.save(user));
    }

    @PostMapping("delete")
    @Operation(description = "删除用户")
    @ApiOptions
    @PreAuthorize("hasAuthority(" + Constants.ROLE_SUPPORT + ")")
    public Result delete(@RequestBody IdDTO idBean) {
        if (this.userService.delete(idBean.getId())) {
            return Result.success();
        }
        return Result.failure(ResultCode.SPECIFIED_QUESTIONED_USER_NOT_EXIST, "删除失败");
    }

    @GetMapping("logout")
    @Operation(description = "登出")
    @ApiOptions
    public Result logout() {
        return Result.success(this.userService.logout());
    }

    @GetMapping("export")
    @Operation(description = "导出")
    @ApiOptions
    public void excelExport() {
        normalExcelExport(this.userService.list());
    }

    @PostMapping("import")
    @Operation(description = "导入")
    @ApiOptions
    public Result excelImport(@RequestParam MultipartFile file) throws IOException {
        List<User> list = excelImport(file.getInputStream());
        return Result.success(this.userService.saveBatch(list));
    }

}

