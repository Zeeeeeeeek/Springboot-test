package com.zhejianglab.spring3dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhejianglab.spring3dao.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author chenze
 * @since 2022-03-24
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}
