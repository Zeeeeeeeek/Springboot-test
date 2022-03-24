package com.zhejianglab.spring3service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhejianglab.spring3dao.entity.User;
import com.zhejianglab.spring3dao.mapper.UserMapper;
import com.zhejianglab.spring3service.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author chenze
 * @since 2022-03-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public Page<User> selectByPage(Integer current, Integer pageSize) {
        Page<User> page = new Page<>(current, pageSize);
        return this.page(page);
    }
}
