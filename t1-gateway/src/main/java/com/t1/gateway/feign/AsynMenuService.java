package com.t1.gateway.feign;

import com.t1.common.entity.Menu;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 异步Menu服务
 *
 * @author Bruce Lee (Copy)
 * @version 1.0
 * @date 2021/8/8
 * <p>
 */
@Component
public class AsynMenuService {
    @Lazy
    @Resource
    private MenuService menuService;

    @Async
    public Future<List<Menu>> findByRoleCodes(String roleCodes) {
        List<Menu> result = menuService.findByRoleCodes(roleCodes);
        return new AsyncResult<>(result);
    }
}
