package com.t1.sys.base.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.t1.sys.base.service.UserService;
import com.t1.common.entity.User;
import com.t1.common.model.LoginAppUser;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users-anon")
@AllArgsConstructor
public class AnonUserController {
    private final UserService userService;

    private QueryWrapper<User> getQueryWrapper(User user) {
        return new QueryWrapper<User>().like(StrUtil.isNotBlank(user.getUserName()), "user_name", user.getUserName()).like(StrUtil.isNotBlank(user.getNickName()), "nick_name", user.getNickName()).eq(StrUtil.isNotBlank(user.getStatus()), "status", user.getStatus())
                .eq(ObjectUtil.isNotNull(user.getDeptId()), "dept_id", user.getDeptId())
                .between(StrUtil.isNotBlank(user.getBeginTime()) && StrUtil.isNotBlank(user.getEndTime()), "create_time", user.getBeginTime(), user.getEndTime()).apply(StrUtil.isNotBlank(user.getSqlFilter()), user.getSqlFilter());
    }


    /**
     * 查询用户登录对象LoginAppUser
     */
    @GetMapping(value = "/login", params = "username")
    public LoginAppUser findLoginAppUserByUsername(String username) {
        return userService.findLoginAppUserByUsername(username);
    }

    @GetMapping("/name/{username}")
    public User findUserByUsername(@PathVariable("username") String username) {

        return userService.findUserByUsername(username);
    }

}
