package com.zhejianglab.spring3common.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenze
 * @date 2022/3/25
 */
public class Sequence {
    private static final AtomicInteger COUNTER = new AtomicInteger();

    public static final int nextValue() {
        int current;
        int next;
        do {
            current = COUNTER.get();
            next = current >= 2147483647 ? 0 : current + 1;
        } while (!COUNTER.compareAndSet(current, next));
        return next;
    }
}
