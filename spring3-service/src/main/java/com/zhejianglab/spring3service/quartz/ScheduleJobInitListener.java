package com.zhejianglab.spring3service.quartz;

import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author chenze
 * @date 2022/3/28
 */
@Component
public class ScheduleJobInitListener implements CommandLineRunner {

    @Resource
    private ScheduleJobService jobService;

    @Override
    public void run(String... strings) throws Exception {
        jobService.startJob();
    }
}