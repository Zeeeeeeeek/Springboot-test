package com.zhejianglab.spring3service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhejianglab.spring3dao.entity.Role;
import com.zhejianglab.spring3dao.mapper.RoleMapper;
import com.zhejianglab.spring3service.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author chenze
 * @since 2022-03-24
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
