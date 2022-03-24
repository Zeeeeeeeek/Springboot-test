package com.zhejianglab.spring3dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhejianglab.spring3dao.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author chenze
 * @since 2022-03-24
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
