package com.zhejianglab.spring3dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhejianglab.spring3dao.entity.Task;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 定时任务 Mapper 接口
 * </p>
 *
 * @author chenze
 * @since 2022-03-28
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {

}
