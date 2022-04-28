package com.t1.sys.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1.common.model.R;
import com.t1.sys.base.service.ClientDetailsService;
import com.t1.sys.base.entity.ClientDetails;
import com.t1.common.utils.StrUtil;
import com.t1.log.annotation.OperLog;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


/**
 * @author Bruce Lee ( copy )
 * @date 2019-10-11 09:13:04
 * @description 授权客户端Controller
 */
@RestController
@AllArgsConstructor
@RequestMapping("/system/clientDetails")
public class ClientDetailsController {

    private final ClientDetailsService clientDetailsService;
    private final PasswordEncoder passwordEncoder;

    private QueryWrapper<ClientDetails> getQueryWrapper(ClientDetails clientDetails) {
        return new QueryWrapper<ClientDetails>().like(StrUtil.isNotBlank(clientDetails.getClientName()), "client_name", clientDetails.getClientName());
    }

    @PreAuthorize("@ps.hasPerm('clientDetails_view')")
    @GetMapping("/list")
    @ResponseBody
    public R list(Page page, ClientDetails clientDetails) {
        IPage<ClientDetails> clientDetailsPage = clientDetailsService.page(page, getQueryWrapper(clientDetails));
        return R.success(clientDetailsPage.getRecords(), clientDetailsPage.getTotal());
    }

    @GetMapping("/{clientId}")
    public R getById(@PathVariable("clientId") String clientId) {
        return R.success(clientDetailsService.getById(clientId));
    }

    @OperLog("终端新增")
    @PreAuthorize("@ps.hasPerm('clientDetails_add')")
    @PostMapping("/save")
    @ResponseBody
    public R save(@RequestBody ClientDetails clientDetails) {
        clientDetails.setClientSecret(passwordEncoder.encode(clientDetails.getClientSecretStr()));
        clientDetailsService.save(clientDetails);
        return R.success();
    }

    @OperLog("终端修改")
    @PreAuthorize("@ps.hasPerm('clientDetails_edit')")
    @PutMapping("/update")
    @ResponseBody
    public R update(@RequestBody ClientDetails clientDetails) {
        clientDetails.setClientSecret(passwordEncoder.encode(clientDetails.getClientSecret()));
        clientDetailsService.updateById(clientDetails);
        return R.success();
    }

    @OperLog("终端删除")
    @PreAuthorize("@ps.hasPerm('clientDetails_del')")
    @DeleteMapping("/remove/{clientId}")
    @ResponseBody
    public R remove(@PathVariable("clientId") String clientId) {
        return R.success(clientDetailsService.removeById(clientId));
    }
}
