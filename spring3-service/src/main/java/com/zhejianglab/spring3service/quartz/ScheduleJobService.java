package com.zhejianglab.spring3service.quartz;

import cn.hutool.core.collection.CollUtil;
import com.zhejianglab.spring3common.constant.Constants;
import com.zhejianglab.spring3common.dto.ResultCode;
import com.zhejianglab.spring3common.exception.CustomException;
import com.zhejianglab.spring3dao.entity.Task;
import com.zhejianglab.spring3service.service.ITaskService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.quartz.DateBuilder.futureDate;

/**
 * @author chenze
 * @date 2022/3/28
 */
@Component
@Slf4j
public class ScheduleJobService {

    @Resource
    private ITaskService taskService;

    @Resource
    private Scheduler scheduler;

    /**
     * 程序启动开始加载全部定时任务
     */
    public void startJob() throws SchedulerException {
        List<Task> taskList = this.taskService.getAllCronTask();
        if (CollUtil.isEmpty(taskList)){
            log.info("定时任务加载数据为空");
            return;
        }
        for (Task task : taskList) {
            CronTrigger cronTrigger;
            JobDetail jobDetail;
            cronTrigger = getCronTrigger(task);
            jobDetail = getJobDetail(task);
            scheduler.scheduleJob(jobDetail,cronTrigger);
            log.info("编号：{}定时任务加载成功",task.getTaskId());
        }
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            log.error("定时任务启动失败",e);
        }
    }

    /**
     * 停止任务
     *
     * @param taskId
     */
    public void stopJob(String taskId) throws SchedulerException {
        scheduler.pauseJob(JobKey.jobKey(taskId));
        log.info("编号：{}定时任务停止",taskId);
    }

    /**
     * 恢复任务
     *
     * @param taskId
     * @throws SchedulerException
     */
    public void resumeJob(String taskId) throws SchedulerException {
        scheduler.resumeJob(JobKey.jobKey(taskId));
        log.info("编号：{}定时任务恢复",taskId);
    }

    /**
     * 添加新的job
     *
     * @param taskId
     * @throws SchedulerConfigException
     */
    public void addJob(String taskId) throws SchedulerException {
        Task task = this.taskService.getTaskByTaskId(taskId);
        InjectJob(task);
    }

    /**
     * 卸载cron job执行计划
     *
     * @param taskId
     * @throws SchedulerException
     */
    public void unloadJob(String taskId) throws SchedulerException {
        // 停止触发器
        scheduler.pauseTrigger(TriggerKey.triggerKey(taskId));
        // 卸载定时任务
        scheduler.unscheduleJob(TriggerKey.triggerKey(taskId));
        // 删除原来的job
        scheduler.deleteJob(JobKey.jobKey(taskId));
        log.info("编号：{}定时任务卸载",taskId);
    }

    /**
     * 重新加载job执行计划
     *
     * @throws Exception
     */
    public void reload(String taskId) throws SchedulerException {
        Task task = this.taskService.getTaskByTaskId(taskId);
        if (task == null){
            throw new CustomException(ResultCode.SCHEDULE_CONFIG_NOT_FOUND);
        }
        String jobCode = task.getTaskId();
        // 获取以前的触发器
        TriggerKey triggerKey = TriggerKey.triggerKey(jobCode);
        // 停止触发器
        scheduler.pauseTrigger(triggerKey);
        // 删除触发器
        scheduler.unscheduleJob(triggerKey);
        // 删除原来的job
        scheduler.deleteJob(JobKey.jobKey(jobCode));

        InjectJob(task);
    }

    /**
     * 组装JobDetail
     *
     * @param task
     * @return
     * @throws ClassNotFoundException
     */
    private JobDetail getJobDetail(Task task)  {

        Class<? extends Job> aClass;
        try {
            aClass = Class.forName(task.getClassName()).asSubclass(Job.class);
        } catch (ClassNotFoundException e) {
            throw new CustomException(ResultCode.SCHEDULE_TASK_CLASS_NOT_FOUND);
        }

        return JobBuilder.newJob()
                .withIdentity(JobKey.jobKey(task.getTaskId()))
                .withDescription(task.getDescription())
                .ofType(aClass).build();
    }

    /**
     * 组装CronTrigger
     *
     * @param task
     * @return
     */
    private CronTrigger getCronTrigger(Task task){
        CronTrigger cronTrigger;
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCron());
        cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(TriggerKey.triggerKey(task.getTaskId()))
                .withSchedule(cronScheduleBuilder)
                .build();
        return cronTrigger;
    }

    /**
     * 组装SimpleTrigger
     *
     * @param task
     * @return
     */
    private SimpleTrigger getSimpleTrigger(Task task){
        // 转换为时间差，秒单位
        int time = (int) (task.getScheduleTime().getTime() - System.currentTimeMillis()) / 1000;
        return (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity(TriggerKey.triggerKey(task.getTaskId()))
                .startAt(futureDate(time, DateBuilder.IntervalUnit.SECOND))
                .build();
    }


    /**
     * 将job注入scheduler
     *
     * @param task
     * @throws ClassNotFoundException
     * @throws SchedulerException
     */
    private void InjectJob(Task task) throws SchedulerException {
        JobDetail jobDetail = getJobDetail(task);
        if(Constants.TASK_EXECUTE_TYPE_CRON.equals(task.getTaskType())){
            CronTrigger cronTrigger = getCronTrigger(task);
            scheduler.scheduleJob(jobDetail, cronTrigger);
        }else{
            SimpleTrigger simpleTrigger = getSimpleTrigger(task);
            scheduler.scheduleJob(jobDetail, simpleTrigger);
        }
        log.info("编号：{}定时任务添加成功",task.getTaskId());
    }
}