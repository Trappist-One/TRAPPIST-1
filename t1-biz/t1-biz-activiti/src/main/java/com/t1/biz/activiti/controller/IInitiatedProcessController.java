package com.t1.biz.activiti.controller;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.t1.biz.activiti.dto.TaskDto;
import com.t1.biz.activiti.service.IInitiatedProcessService;
import com.t1.common.annotation.LoginUser;
import com.t1.common.entity.User;
import com.t1.common.model.R;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.Map;

/**
 * <p>
 * 我发起流程控制层
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @Date: 2021/4/12
 */
@RestController
@RequestMapping("/activiti/IInitiatedProcess")
@AllArgsConstructor
public class IInitiatedProcessController {


    private final IInitiatedProcessService IInitiatedProcessService;


    @GetMapping("/list")
    public R list(@LoginUser User user, @RequestParam Map<String, Object> params) {
        IPage<TaskDto> taskIPage = IInitiatedProcessService.list(params, user.getUserName());
        return R.success(taskIPage.getRecords(), taskIPage.getTotal());
    }

    /** 作废流程 **/
    @GetMapping("/abolishProcess")
    public R abolishProcess(String procInstId,String businessKey) throws ClassNotFoundException {
        IInitiatedProcessService.abolishProcess(procInstId,businessKey);
        return R.success();
    }

    /** 流程追踪 **/
    @GetMapping("/tracking")
    public ResponseEntity tracking(@RequestParam String processInstanceId) {
        InputStream imageStream = IInitiatedProcessService.fetchProcessTrackingPicture(processInstanceId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity(IoUtil.readBytes(imageStream), headers, HttpStatus.CREATED);
    }

    /** 流程历史跟踪信息 **/
    @GetMapping("/detailedProcess")
    public R listDetailedProcess(@RequestParam Map<String, Object> params) {
        IPage<TaskDto> taskIPage = IInitiatedProcessService.listDetailedProcess(params);
        return R.success(taskIPage.getRecords(), taskIPage.getTotal());
    }


}
