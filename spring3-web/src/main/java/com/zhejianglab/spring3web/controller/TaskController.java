package com.zhejianglab.spring3web.controller;


import com.zhejianglab.spring3common.Annotation.ApiOptions;
import com.zhejianglab.spring3common.dto.Result;
import com.zhejianglab.spring3dao.entity.Task;
import com.zhejianglab.spring3service.service.ITaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 定时任务 前端控制器
 * </p>
 *
 * @author chenze
 * @since 2022-03-28
 */
@RestController
@Tag(name = "定时任务控制器", description = "定时任务添加")
@RequestMapping("/task")
public class TaskController {

    @Resource
    private ITaskService taskService;

    @PostMapping("save")
    @Operation(description = "保存任务")
    @ApiOptions
    public Result save(@RequestBody @Valid Task task) {
        return Result.success(this.taskService.saveOrUpdate(task));
    }

}

