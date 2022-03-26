package com.zhejianglab.spring3web.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhejianglab.spring3common.Annotation.ApiOptions;
import com.zhejianglab.spring3common.dto.DataList;
import com.zhejianglab.spring3common.dto.Result;
import com.zhejianglab.spring3dao.entity.User;
import com.zhejianglab.spring3service.service.IUserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author chenze
 * @since 2022-03-24
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @GetMapping("listByPage")
    @ApiOptions
    public Result listByPage(@RequestParam(required = false, defaultValue = "1") Integer current,
                             @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
        Page<User> page = this.userService.selectByPage(current, pageSize);
        return Result.success(new DataList<>(page.getTotal(), page.getRecords()));
    }

    @PostMapping("save")
    @ApiOptions
    public Result save(@RequestBody @Valid User user) {
        user.setPassword(SecureUtil.md5(user.getPassword()));
        return Result.success(this.userService.save(user));
    }

    @GetMapping("logout")
    @ApiOptions
    public Result logout() {
        return Result.success(this.userService.logout());
    }

}

