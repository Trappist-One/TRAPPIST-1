package com.t1.common.lock;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 锁对象抽象
 *
 * @author Bruce Lee(copy)
 * @date 2020/7/28
 * <p>
 */
@AllArgsConstructor
public class T1Lock implements AutoCloseable {
    @Getter
    private final Object lock;

    private final DistributedLock locker;

    @Override
    public void close() throws Exception {
        locker.unlock(lock);
    }
}
