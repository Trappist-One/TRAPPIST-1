package com.t1.sys.monitor.controller;

import com.t1.common.model.R;
import com.t1.sys.monitor.server.Server;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 *
 * @author Bruce Lee ( copy )
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController {

    @GetMapping()
    public R getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return R.success(server);
    }
}
