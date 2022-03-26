package com.zhejianglab.spring3service.service;

import org.springframework.stereotype.Service;

/**
 * @author chenze
 * @date 2022/3/26
 */
@Service
public interface AsyncService {
    /**
     * 异步任务测试
     */
    void executeAsync();
}
