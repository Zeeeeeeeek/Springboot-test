package com.zhejianglab.spring3service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhejianglab.spring3common.constant.Constants;
import com.zhejianglab.spring3common.dto.ResultCode;
import com.zhejianglab.spring3common.eunm.RoleEnums;
import com.zhejianglab.spring3common.exception.CustomException;
import com.zhejianglab.spring3common.utils.JwtUtil;
import com.zhejianglab.spring3dao.dto.RefreshDTO;
import com.zhejianglab.spring3dao.dto.UserDTO;
import com.zhejianglab.spring3dao.entity.User;
import com.zhejianglab.spring3dao.mapper.UserMapper;
import com.zhejianglab.spring3dao.vo.JwtVo;
import com.zhejianglab.spring3dao.vo.UserVo;
import com.zhejianglab.spring3service.holder.SessionLocal;
import com.zhejianglab.spring3service.redis.RedisKeyUtil;
import com.zhejianglab.spring3service.redis.RedisUtil;
import com.zhejianglab.spring3service.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.zhejianglab.spring3common.utils.JwtUtil.*;

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

    @Resource
    private RedisUtil redisUtil;

    @Override
    public Page<User> selectByPage(Integer current, Integer pageSize) {
        Page<User> page = new Page<>(current, pageSize);
        return this.page(page, new QueryWrapper<User>().lambda().eq(User::getStatus, Constants.USER_VALID));
    }

    @Override
    public User findByUserNameAndPass(String userName, String password) {
        return this.getOne(new QueryWrapper<User>().lambda().eq(User::getUserName, userName).eq(User::getPassword, password), false);
    }

    @Override
    public boolean save(User user) {
        if (user.getId() != null && this.saveOrUpdate(user)) {
            String userKey = RedisKeyUtil.userInfo(user.getId().toString());
            if (redisUtil.hasKey(userKey)) {
                UserVo userVo = new UserVo();
                BeanUtil.copyProperties(user, userVo);
                redisUtil.set(userKey, user);
            }
            return true;
        }
        return this.saveOrUpdate(user);
    }

    @Override
    public boolean delete(Integer id) {
        return this.update(new UpdateWrapper<User>().lambda().set(User::getStatus, Constants.USER_INVALID));
    }

    @Override
    public boolean logout() {
        String userId = SessionLocal.getUserInfo().getUserId().toString();
        String key = RedisKeyUtil.userTokenKey(userId);
        String refreshKey = RedisKeyUtil.userRefreshTokenKey(userId);
        String userInfoKey = RedisKeyUtil.userInfo(userId);
        redisUtil.del(key);
        redisUtil.del(refreshKey);
        redisUtil.del(userInfoKey);
        return true;
    }

    @Override
    public JwtVo login(UserDTO userDTO) {
        User user = this.findByUserNameAndPass(userDTO.getUserName(), userDTO.getPassword());
        if (null == user) {
            throw new CustomException(ResultCode.USER_LOGIN_ERROR);
        }
        String key = RedisKeyUtil.userTokenKey(String.valueOf(user.getId()));
        String refreshKey = RedisKeyUtil.userRefreshTokenKey(String.valueOf(user.getId()));
        if (redisUtil.hasKey(key)) {
            return checkLogin(key, refreshKey);
        }
        String userKey = RedisKeyUtil.userInfo(String.valueOf(user.getId()));
        UserVo userVo = new UserVo();
        BeanUtil.copyProperties(user, userVo);
        redisUtil.set(userKey, userVo);
        return generateJwt(key, refreshKey, String.valueOf(user.getId()), RoleEnums.parse(user.getRoleType()).getValue());
    }

    @Override
    public JwtVo refresh(RefreshDTO refreshTokenBean) {
        Map<String, Object> map = JwtUtil.getClaims(refreshTokenBean.getRefreshToken());
        String userId = (String) map.get(ID);
        String role = (String) map.get(ROLE);
        String key = RedisKeyUtil.userTokenKey(userId);
        String refreshKey = RedisKeyUtil.userRefreshTokenKey(userId);
        String cachedRefreshToken = (String) redisUtil.get(refreshKey);
        if (!cachedRefreshToken.equals(refreshTokenBean.getRefreshToken())) {
            throw new CustomException(ResultCode.TOKEN_NO_ACCESS, "该refreshToken已失效");
        }
        return generateJwt(key, refreshKey, userId, role);
    }

    private JwtVo checkLogin(String tokenKey, String refreshTokenKey) {
        String token = (String) redisUtil.get(tokenKey);
        String refreshToken = (String) redisUtil.get(refreshTokenKey);
        String expire = String.valueOf(redisUtil.getExpire(tokenKey));
        return JwtVo.builder().jwtToken(token).refreshToken(refreshToken).expiration(expire).build();
    }

    private JwtVo generateJwt(String tokenKey, String refreshTokenKey, String userId, String role) {
        Date current = new Date();
        String newToken = JwtUtil.generateToken(role, userId, current);
        String newRefreshToken = JwtUtil.generateRefreshToken(role, userId, current);
        redisUtil.set(tokenKey, newToken, EXPIRATION_TIME / 1000, TimeUnit.SECONDS);
        redisUtil.set(refreshTokenKey, newRefreshToken, REFRESH_TIME / 1000, TimeUnit.SECONDS);
        return JwtVo.builder().jwtToken(newToken).refreshToken(newRefreshToken).expiration(String.valueOf(EXPIRATION_TIME / 1000)).build();
    }
}
