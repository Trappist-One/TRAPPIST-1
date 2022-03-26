package com.t1.sys.rbac.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1.sys.rbac.service.RoleService;
import com.t1.sys.rbac.service.UserRoleService;
import com.t1.sys.rbac.service.UserService;
import com.t1.sys.rbac.vo.ResultVo;
import com.t1.sys.rbac.vo.UserVo;
import com.t1.common.annotation.LoginUser;
import com.t1.common.config.GlobalConfig;
import com.t1.common.constant.CommonConstants;
import com.t1.common.constant.SqlConstants;
import com.t1.common.entity.Role;
import com.t1.common.entity.User;
import com.t1.common.model.R;
import com.t1.common.utils.ExcelUtil;
import com.t1.common.utils.UploadUtil;
import com.t1.db.annotation.DataFilter;
import com.t1.log.annotation.OperLog;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户信息
 *
 * @author Bruce Lee ( copy )
 */
@RestController
@RequestMapping("/system/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JdbcTemplate jdbcTemplate;

    private QueryWrapper<User> getQueryWrapper(User user) {
        return new QueryWrapper<User>().like(StrUtil.isNotBlank(user.getUserName()), "user_name", user.getUserName()).like(StrUtil.isNotBlank(user.getNickName()), "nick_name", user.getNickName()).eq(StrUtil.isNotBlank(user.getStatus()), "status", user.getStatus())
                .eq(ObjectUtil.isNotNull(user.getDeptId()), "dept_id", user.getDeptId())
                .between(StrUtil.isNotBlank(user.getBeginTime()) && StrUtil.isNotBlank(user.getEndTime()), "create_time", user.getBeginTime(), user.getEndTime()).apply(StrUtil.isNotBlank(user.getSqlFilter()), user.getSqlFilter());
    }

    @PreAuthorize("@ps.hasPerm('user_view')")
    @GetMapping("/list")
    @DataFilter
    public R list(Page page, User user) {
        IPage<User> userIPage = userService.page(page, getQueryWrapper(user));
        return R.success(userIPage.getRecords(), userIPage.getTotal());
    }

    @PreAuthorize("@ps.hasPerm('user_view')")
    @GetMapping("/userList")
    public R userList(User user) {
        List<UserVo> userList = userService.list(getQueryWrapper(user)).stream().map(userInfo -> {
            UserVo userVo = new UserVo();
            userVo.setId(userInfo.getId());
            userVo.setNickName(userInfo.getNickName());
            return userVo;
        }).collect(Collectors.toList());
        return R.success(userList);
    }

    @GetMapping("/{id}")
    public R getById(@PathVariable("id") Integer id) {
        User user = userService.getById(id);
        List<Role> roleList = roleService.list();
        Set<Integer> roles = roleList.parallelStream().map(Role::getId).collect(Collectors.toSet());
        if (user != null) {
            user.setRoles(roles);
        }
        return R.success(ResultVo.builder().result(user).extend(roleList).build());
    }

    /**
     * 获取当前用户全部信息
     *
     * @return 用户信息
     */
    @GetMapping("/info")
    public R info(@LoginUser(isFull = true) User user) {
        if (user == null && user.getId() != null) {
            return R.error("获取当前用户信息失败");
        }

        Set<Integer> roles = user.getRoles();
        List<String> roleList = new ArrayList<>();
        roles.forEach(roleId -> {
            roleList.add(CommonConstants.ROLE + roleId);
        });
        user.setRoleList(ArrayUtil.toArray(roleList, String.class));

        Set<String> permissions = new HashSet<>();
        roles.forEach(roleId -> {
            permissions.addAll(jdbcTemplate.query(SqlConstants.QUERY_PREMS, new Object[]{roleId}, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int i)
                        throws SQLException {
                    return rs.getString(1);
                }
            }));
        });
        user.setPermissions(permissions);
        return R.success(user);
    }

    @OperLog("用户新增")
    @PreAuthorize("@ps.hasPerm('user_add')")
    @PostMapping("/save")
    public R save(@RequestBody User user) {
        if (!StrUtil.isEmptyIfStr(user.getId()) && User.isAdmin(user.getId())) {
            return R.error("不允许修改超级管理员");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return R.success();
    }

    @OperLog("用户修改")
    @PreAuthorize("@ps.hasPerm('user_edit')")
    @PutMapping("/update")
    public R update(@RequestBody User user) {
        userService.saveUser(user);
        return R.success();
    }

    @OperLog("用户删除")
    @PreAuthorize("@ps.hasPerm('user_del')")
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable Integer[] id) {
        if (ArrayUtil.contains(id, 1)) {
            return R.error("不允许删除超级管理员");
        }
        userService.removeByIds(Arrays.asList(id));
        return R.success();
    }

    @GetMapping("/profile")
    public R profile(@LoginUser(isFull = true) User user) {
        if (user != null) {
            String roleNames = user.getRoles().stream().map(roleId -> roleService.getById(roleId + "").getName())
                    .collect(Collectors.joining(","));
            user.setRoleNames(roleNames);
            user.setPassword(null);
            return R.success(user);
        } else {
            return R.error("登录信息已过期，请重新登录");
        }
    }

    @OperLog("用户信息修改")
    @PreAuthorize("@ps.hasPerm('user_edit')")
    @PutMapping("/updateProfile")
    public R updateProfile(@RequestBody User user) {
        userService.update(new UpdateWrapper<User>().eq("id", user.getId()).set("nick_name", user.getNickName()).set(StrUtil.isNotBlank(user.getPhone()), "phone", user.getPhone()).set("email", user.getEmail()).set("sex", user.getSex()));
        return R.success();
    }

    @OperLog("用户头像修改")
    @PreAuthorize("@ps.hasPerm('user_edit')")
    @PutMapping("/updateAvatar")
    public R updateAvatar(@LoginUser(isFull = true) User user, @RequestParam("avatarfile") MultipartFile file, HttpServletRequest request) {
        String avatar = "/profile/avatar/" + UploadUtil.fileUp(file, GlobalConfig.getAvatarPath(), "avatar" + new Date().getTime());
        userService.update(new UpdateWrapper<User>().eq("id", user.getId()).set("avatar", avatar));
        return R.success(avatar);
    }

    @OperLog("用户密码修改")
    @PreAuthorize("@ps.hasPerm('user_edit')")
    @PutMapping("/updatePwd")
    public R updatePwd(@LoginUser(isFull = true) User user1, User user) {
        if (user1 != null && passwordEncoder.matches(user.getPassword(), user1.getPassword())) {
            userService.update(new UpdateWrapper<User>().eq("id", user1.getId()).set("password", passwordEncoder.encode(user.getNewPassword())));
            return R.success();
        } else {
            return R.error("原密码有误，请重试");
        }
    }

    @OperLog("用户密码重置")
    @PreAuthorize("@ps.hasPerm('user_reset')")
    @PutMapping("/resetPwd")
    public R resetPwd(@RequestBody User user) {
        userService.update(new UpdateWrapper<User>().eq("id", user.getId()).set("password", passwordEncoder.encode(user.getPassword())));
        return R.success();
    }

    @OperLog("用户状态更改")
    @PreAuthorize("@ps.hasPerm('user_edit')")
    @PutMapping("/changeStatus")
    public R changeStatus(@RequestBody User user) {
        if (User.isAdmin(user.getId())) {
            return R.error("不允许修改超级管理员用户");
        }
        userService.update(new UpdateWrapper<User>().eq("id", user.getId()).set("status", user.getStatus()));
        return R.success();
    }

    @SneakyThrows
    @OperLog("用户数据导出")
    @PreAuthorize("@ps.hasPerm('user_export')")
    @GetMapping("/exportUser")
    public R exportUser(User user) {
        List<User> list = userService.list(getQueryWrapper(user));
        ExcelUtil<User> util = new ExcelUtil<User>(User.class);
        return util.exportExcel(list, "用户数据");
    }

    @SneakyThrows
    @OperLog("用户数据导入")
    @PreAuthorize("@ps.hasPerm('user_import')")
    @PostMapping("/importUser")
    public R importUser(MultipartFile file, boolean updateSupport) {
        ExcelUtil<User> util = new ExcelUtil<User>(User.class);
        List<User> userList = util.importExcel(file.getInputStream());
        String message = userService.importUser(userList, updateSupport);
        return R.success(message);
    }

    @GetMapping("/importTemplate")
    public R importTemplate() {
        ExcelUtil<User> util = new ExcelUtil<User>(User.class);
        return util.importTemplateExcel("用户数据");
    }

}
