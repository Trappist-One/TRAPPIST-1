package com.t1.job.executor.handler;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 京东自动抢购
 */
@Component
@Slf4j
public class JDAutoGoJob {

    @XxlJob("autoGoSonyA7M4")
    public void autoGoSonyA7M4() {
        // 抢购索尼A7M4相机
    }
}
