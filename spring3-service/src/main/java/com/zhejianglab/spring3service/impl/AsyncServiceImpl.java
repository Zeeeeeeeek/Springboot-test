package com.zhejianglab.spring3service.impl;

import com.zhejianglab.spring3service.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author chenze
 * @date 2022/3/26
 */
@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {

    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync() {
        log.info("start executeAsync :{}", Thread.currentThread().getName());
        System.out.println("异步线程要做的事情");
        System.out.println("可以在这里执行批量插入等耗时的事情");
        log.info("end executeAsync :{}", Thread.currentThread().getName());
    }
}
