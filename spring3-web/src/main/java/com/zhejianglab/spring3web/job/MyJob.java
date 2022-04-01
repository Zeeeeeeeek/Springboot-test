package com.zhejianglab.spring3web.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author chenze
 * @date 2022/3/28
 */
@Slf4j
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        log.info("定时任务开始: {}, 任务标识: {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), jobDetail.getKey().getName());
    }
}