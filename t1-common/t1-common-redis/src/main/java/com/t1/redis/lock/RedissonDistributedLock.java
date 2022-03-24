package com.t1.redis.lock;

import com.t1.common.constant.CommonConstants;
import com.t1.common.exception.LockException;
import com.t1.common.lock.DistributedLock;
import com.t1.common.lock.T1Lock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.concurrent.TimeUnit;

/**
 * redisson分布式锁实现，基本锁功能的抽象实现
 * 本接口能满足绝大部分的需求，高级的锁功能，请自行扩展或直接使用原生api
 * https://gitbook.cn/gitchat/activity/5f02746f34b17609e14c7d5a
 *
 * @author Bruce Lee(copy)
 * @date 2020/5/5
 * <p>
 */
@ConditionalOnClass(RedissonClient.class)
@ConditionalOnProperty(prefix = "t1.lock", name = "lockerType", havingValue = "REDIS", matchIfMissing = true)
public class RedissonDistributedLock implements DistributedLock {
    @Autowired
    private RedissonClient redisson;

    private T1Lock getLock(String key, boolean isFair) {
        RLock lock;
        if (isFair) {
            lock = redisson.getFairLock(CommonConstants.LOCK_KEY_PREFIX + ":" + key);
        } else {
            lock =  redisson.getLock(CommonConstants.LOCK_KEY_PREFIX + ":" + key);
        }
        return new T1Lock(lock, this);
    }

    @Override
    public T1Lock lock(String key, long leaseTime, TimeUnit unit, boolean isFair) {
        T1Lock t1Lock = getLock(key, isFair);
        RLock lock = (RLock) t1Lock.getLock();
        lock.lock(leaseTime, unit);
        return t1Lock;
    }

    @Override
    public T1Lock tryLock(String key, long waitTime, long leaseTime, TimeUnit unit, boolean isFair) throws InterruptedException {
        T1Lock t1Lock = getLock(key, isFair);
        RLock lock = (RLock) t1Lock.getLock();
        if (lock.tryLock(waitTime, leaseTime, unit)) {
            return t1Lock;
        }
        return null;
    }

    @Override
    public void unlock(Object lock) {
        if (lock != null) {
            if (lock instanceof RLock) {
                RLock rLock = (RLock)lock;
                if (rLock.isLocked()) {
                    rLock.unlock();
                }
            } else {
                throw new LockException("requires RLock type");
            }
        }
    }
}
