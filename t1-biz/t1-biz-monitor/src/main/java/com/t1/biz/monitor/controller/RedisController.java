package com.t1.biz.monitor.controller;

import com.t1.common.model.R;
import com.t1.biz.monitor.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bruce Lee ( copy )
 * @date 2020-05-16
 * @description redis数据
 */
@RestController
@AllArgsConstructor
@RequestMapping("/monitor/redis")
public class RedisController {

    private final RedisService redisService;

    @GetMapping()
    public R getInfo() {
        return R.success(redisService.getInfo());
    }
}
