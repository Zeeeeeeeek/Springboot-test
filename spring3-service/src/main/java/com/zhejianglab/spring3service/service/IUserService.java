package com.zhejianglab.spring3service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhejianglab.spring3dao.entity.User;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author chenze
 * @since 2022-03-24
 */
public interface IUserService extends IService<User> {

    Page<User> selectByPage(Integer current, Integer pageSize);

}
