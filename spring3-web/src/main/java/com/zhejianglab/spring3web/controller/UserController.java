package com.zhejianglab.spring3web.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhejianglab.spring3common.dto.DataList;
import com.zhejianglab.spring3common.dto.Result;
import com.zhejianglab.spring3dao.entity.User;
import com.zhejianglab.spring3service.redis.RedisKeyUtil;
import com.zhejianglab.spring3service.redis.RedisUtil;
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
public class UserController extends BaseController{

    @Resource
    private IUserService userService;

    @Resource
    private RedisUtil redisUtil;

    @GetMapping("listByPage")
    public Result listByPage(@RequestParam(required = false,defaultValue = "1") Integer current,
                             @RequestParam(required = false,defaultValue = "20") Integer pageSize){
        Page<User> page = this.userService.selectByPage(current,pageSize);
        return Result.success(new DataList<>(page.getTotal(),page.getRecords()));
    }

    @PostMapping("save")
    public Result save(@RequestBody @Valid User user){
        user.setPassword(SecureUtil.md5(user.getPassword()));
        return Result.success(this.userService.saveOrUpdate(user));
    }

    @GetMapping("logout")
    public Result logout(){
        String key = RedisKeyUtil.userTokenKey(getUserId());
        String refreshKey = RedisKeyUtil.userRefreshTokenKey(getUserId());
        String userInfoKey = RedisKeyUtil.userInfo(getUserId());
        redisUtil.del(key);
        redisUtil.del(refreshKey);
        redisUtil.del(userInfoKey);
        return Result.success();
    }

}

