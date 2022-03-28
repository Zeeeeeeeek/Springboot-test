package com.zhejianglab.spring3service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhejianglab.spring3common.constant.Constants;
import com.zhejianglab.spring3dao.entity.Task;
import com.zhejianglab.spring3dao.mapper.TaskMapper;
import com.zhejianglab.spring3service.service.ITaskService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 定时任务 服务实现类
 * </p>
 *
 * @author chenze
 * @since 2022-03-28
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

    @Override
    public Task getTaskByTaskId(String taskId) {
        return this.getOne(
                Wrappers.<Task>lambdaQuery()
                        .eq(Task::getTaskId, taskId));
    }

    @Override
    public List<Task> getAllCronTask() {
        return this.list(
                Wrappers.<Task>lambdaQuery()
                        .eq(Task::getStatus, Constants.STATUS_VALID)
                        .eq(Task::getTaskType, Constants.TASK_EXECUTE_TYPE_CRON));
    }

    @Override
    public boolean setTaskStatus(String taskId, Integer status) {
        return this.update(
                Wrappers.<Task>lambdaUpdate()
                        .eq(Task::getTaskId,taskId)
                        .set(Task::getStatus,status));
    }
}
