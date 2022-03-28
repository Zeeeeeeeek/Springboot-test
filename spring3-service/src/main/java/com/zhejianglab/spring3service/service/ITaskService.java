package com.zhejianglab.spring3service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhejianglab.spring3dao.entity.Task;

import java.util.List;

/**
 * <p>
 * 定时任务 服务类
 * </p>
 *
 * @author chenze
 * @since 2022-03-28
 */
public interface ITaskService extends IService<Task> {

    Task getTaskByTaskId(String taskId);

    List<Task> getAllCronTask();

    boolean setTaskStatus(String taskId, Integer status);

}
