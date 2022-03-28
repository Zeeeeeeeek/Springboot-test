package com.zhejianglab.spring3dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 定时任务
 * </p>
 *
 * @author chenze
 * @since 2022-03-28
 */
@Getter
@Setter
@TableName("task")
@Schema(name = "Task对象", description = "定时任务")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(name = "任务id")
    @TableField("task_id")
    private String taskId;

    @Schema(name = "cron表达式")
    @TableField("cron")
    private String cron;

    @Schema(name = "job引用地址")
    @TableField("class_name")
    private String className;

    @Schema(name = "描述")
    @TableField("description")
    private String description;

    @Schema(name = "定时任务状态 0 停用,1启用")
    @TableField("status")
    private Integer status;

    @Schema(name = "任务执行类型:  0单次执行 1 循环执行")
    @TableField("task_type")
    private Integer taskType;

    @Schema(name = "单次任务启动时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField("schedule_time")
    private Date scheduleTime;

    @Schema(name = "创建时间")
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @Schema(name = "修改时间")
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

}
