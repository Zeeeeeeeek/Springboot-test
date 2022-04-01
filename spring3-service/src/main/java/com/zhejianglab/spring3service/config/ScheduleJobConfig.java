package com.zhejianglab.spring3service.config;

import jakarta.annotation.Resource;
import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.concurrent.Executor;

/**
 * @author chenze
 * @date 2022/3/28
 */
@Configuration
@EnableScheduling
public class ScheduleJobConfig {

    @Resource(name = "asyncServiceExecutor")
    private Executor asyncServiceExecutor;

    @Resource
    private JobFactory jobFactory;

    @Bean("schedulerFactoryBean")
    public SchedulerFactoryBean createFactoryBean() {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setJobFactory(jobFactory);
        factoryBean.setTaskExecutor(asyncServiceExecutor);
        factoryBean.setOverwriteExistingJobs(true);
        return factoryBean;
    }

    //通过这个类对定时任务进行操作
    @Bean
    public Scheduler scheduler(@Qualifier("schedulerFactoryBean") SchedulerFactoryBean factoryBean) {
        return factoryBean.getScheduler();
    }
}