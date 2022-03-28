package com.zhejianglab.spring3web.controller;

import cn.hutool.crypto.SecureUtil;
import com.zhejianglab.spring3common.Annotation.ApiOptions;
import com.zhejianglab.spring3common.constant.Constants;
import com.zhejianglab.spring3common.dto.Result;
import com.zhejianglab.spring3service.quartz.ScheduleJobService;
import com.zhejianglab.spring3service.service.ITaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenze
 * @date 2022/3/24
 */
@RestController
@Tag(name = "工具类控制器", description = "工具类操作")
@RequestMapping("tool")
public class ToolController {

    @Resource
    private ScheduleJobService jobService;

    @Resource
    private ITaskService taskService;

    @GetMapping("enpt")
    @Operation(description = "MD5加密")
    @ApiOptions(login = false)
    public Result enpt(String password) {
        return Result.success(SecureUtil.md5(password));
    }

    @GetMapping ("add")
    @Operation(description = "添加新job")
    @ApiOptions(login = false)
    public Result addJob(String taskId) throws SchedulerException {
        this.jobService.addJob(taskId);
        this.taskService.setTaskStatus(taskId, Constants.STATUS_VALID);
        return Result.success();
    }
    @GetMapping("resume")
    @Operation(description = "恢复job")
    @ApiOptions(login = false)
    public Result resumeJob(String taskId) throws SchedulerException {
        this.jobService.resumeJob(taskId);
        this.taskService.setTaskStatus(taskId, Constants.STATUS_VALID);
        return Result.success();
    }
    @GetMapping("stop")
    @Operation(description = "停止job")
    @ApiOptions(login = false)
    public Result stopJob(String taskId) throws SchedulerException {
        this.jobService.stopJob(taskId);
        this.taskService.setTaskStatus(taskId, Constants.STATUS_INVALID);
        return Result.success();
    }
    @GetMapping("unload")
    @Operation(description = "卸载job")
    @ApiOptions(login = false)
    public Result unloadJob(String taskId) throws SchedulerException {
        this.jobService.unloadJob(taskId);
        this.taskService.setTaskStatus(taskId, Constants.STATUS_INVALID);
        return Result.success();
    }
}
