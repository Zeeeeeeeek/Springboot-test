package com.zhejianglab.spring3web.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhejianglab.spring3common.dto.DataList;
import com.zhejianglab.spring3common.dto.Result;
import com.zhejianglab.spring3dao.entity.User;
import com.zhejianglab.spring3service.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Result listByPage(@RequestParam(required = false,defaultValue = "1") Integer current,
                             @RequestParam(required = false,defaultValue = "20") Integer pageSize){
        Page<User> page = this.userService.selectByPage(current,pageSize);
        return Result.success(new DataList<>(page.getTotal(),page.getRecords()));
    }

}

