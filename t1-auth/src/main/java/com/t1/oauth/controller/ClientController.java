package com.t1.oauth.controller;

import com.t1.common.model.PageResult;
import com.t1.common.model.R;
import com.t1.oauth.dto.ClientDto;
import com.t1.oauth.model.Client;
import com.t1.oauth.service.IClientService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import com.t1.log.annotation.OperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 应用相关接口
 *
 * @author Bruce Lee(copy)
 * <p>
 */
@Api(tags = "应用")
@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private IClientService clientService;

    @GetMapping("/list")
    @OperLog("应用列表")
    public PageResult<Client> list(@RequestParam Map<String, Object> params) {
        return clientService.listClient(params, true);
    }

    @GetMapping("/{id}")
    @OperLog("根据id获取应用")
    public Client get(@PathVariable Long id) {
        return clientService.getById(id);
    }

    @GetMapping("/all")
    @OperLog("所有应用")
    public R<List<Client>> allClient() {
        PageResult<Client> page = clientService.listClient(Maps.newHashMap(), false);
        return R.success(page.getData());
    }

    @DeleteMapping("/{id}")
    @OperLog("删除应用")
    public void delete(@PathVariable Long id) {
        clientService.delClient(id);
    }

    @PostMapping("/saveOrUpdate")
    @OperLog("保存或者修改应用")
    public R saveOrUpdate(@RequestBody ClientDto clientDto) throws Exception {
        return clientService.saveClient(clientDto);
    }
}
