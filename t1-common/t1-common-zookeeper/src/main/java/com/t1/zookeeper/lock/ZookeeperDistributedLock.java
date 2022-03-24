package com.t1.zookeeper.lock;

import com.t1.common.constant.CommonConstants;
import com.t1.common.exception.LockException;
import com.t1.common.lock.DistributedLock;
import com.t1.common.lock.T1Lock;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * zookeeper分布式锁实现
 *
 * @author Bruce Lee(copy)
 * @version 1.0
 * @date 2021/4/3
 * <p>
 */
@Component
@ConditionalOnProperty(prefix = "t1.lock", name = "lockerType", havingValue = "ZK")
public class ZookeeperDistributedLock implements DistributedLock {
    @Resource
    private CuratorFramework client;

    private T1Lock getLock(String key) {
        InterProcessMutex lock = new InterProcessMutex(client, getPath(key));
        return new T1Lock(lock, this);
    }

    @Override
    public T1Lock lock(String key, long leaseTime, TimeUnit unit, boolean isFair) throws Exception {
        T1Lock t1Lock = this.getLock(key);
        InterProcessMutex ipm = (InterProcessMutex) t1Lock.getLock();
        ipm.acquire();
        return t1Lock;
    }

    @Override
    public T1Lock tryLock(String key, long waitTime, long leaseTime, TimeUnit unit, boolean isFair) throws Exception {
        T1Lock t1Lock = this.getLock(key);
        InterProcessMutex ipm = (InterProcessMutex) t1Lock.getLock();
        if (ipm.acquire(waitTime, unit)) {
            return t1Lock;
        }
        return null;
    }

    @Override
    public void unlock(Object lock) throws Exception {
        if (lock != null) {
            if (lock instanceof InterProcessMutex) {
                InterProcessMutex ipm = (InterProcessMutex)lock;
                if (ipm.isAcquiredInThisProcess()) {
                    ipm.release();
                }
            } else {
                throw new LockException("requires InterProcessMutex type");
            }
        }
    }

    private String getPath(String key) {
        return CommonConstants.PATH_SPLIT + CommonConstants.LOCK_KEY_PREFIX + CommonConstants.PATH_SPLIT + key;
    }
}
