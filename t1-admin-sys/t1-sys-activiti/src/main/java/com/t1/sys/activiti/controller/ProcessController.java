package com.t1.sys.activiti.controller;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.t1.sys.activiti.dto.ProcessDto;
import com.t1.sys.activiti.service.ProcessService;
import com.t1.common.model.R;
import com.t1.log.annotation.OperLog;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.Map;

/**
 * @author Bruce Lee ( copy )
 * @date 2020/4/4
 * @description 流程 controller
 */
@RestController
@AllArgsConstructor
@RequestMapping("/activiti/process")
public class ProcessController {

    private final ProcessService processService;

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        IPage<ProcessDto> processIPage = processService.list(params);
        return R.success(processIPage.getRecords(), processIPage.getTotal());
    }

     @OperLog("读取资源")
    @GetMapping(value = "/resource")
    public ResponseEntity resource(@RequestParam String procInsId, @RequestParam String procDefId, @RequestParam String resType) {

        HttpHeaders headers = new HttpHeaders();

        if ("xml".equals(resType)) {
            headers.setContentType(MediaType.APPLICATION_XML);
        } else {
            headers.setContentType(MediaType.IMAGE_PNG);
        }

        InputStream resourceAsStream = processService.readResource(procInsId, procDefId, resType);
        return new ResponseEntity(IoUtil.readBytes(resourceAsStream), headers, HttpStatus.CREATED);
    }

     @OperLog("流程状态改变")
    @PutMapping("/changeStatus")
    public R changeStatus(@RequestParam String procDefId, @RequestParam String status) {
        return R.success(processService.changeStatus(procDefId, status));
    }

     @OperLog("流程删除")
    @DeleteMapping("/remove/{deployId}")
    public R removeProcIns(@PathVariable String deployId) {
        return R.success(processService.removeProcIns(deployId));
    }

}
