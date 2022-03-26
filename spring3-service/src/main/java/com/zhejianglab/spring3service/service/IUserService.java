package com.zhejianglab.spring3service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhejianglab.spring3dao.dto.RefreshTokenBean;
import com.zhejianglab.spring3dao.dto.UserDTO;
import com.zhejianglab.spring3dao.entity.User;
import com.zhejianglab.spring3dao.vo.JwtVo;

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

    User findByUserNameAndPass(String userName, String password);

    boolean save(User user);

    boolean delete(Integer id);

    boolean logout();

    JwtVo login(UserDTO userDTO);

    JwtVo refresh(RefreshTokenBean refreshTokenBean);

}
