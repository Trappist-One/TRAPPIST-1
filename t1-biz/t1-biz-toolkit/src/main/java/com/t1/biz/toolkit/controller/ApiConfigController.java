package com.t1.biz.toolkit.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.t1.biz.toolkit.entity.Apiinfo;
import com.t1.biz.toolkit.service.ApiinfoService;
import com.t1.common.model.R;
import com.t1.common.utils.StrUtil;
import io.swagger.annotations.Api;
import com.t1.log.annotation.OperLog;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Bruce Lee ( copy )
 * @date 2020/6/6
 * @description API解析器
 */
@Api("API解析器")
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ApiConfigController {

    private final ApiinfoService apiinfoService;
    private final JdbcTemplate jdbcTemplate;

    @OperLog("API解析器")
    @RequestMapping("/{module}/{method}")
    public R getById(@PathVariable("module") String module, @PathVariable("method") String method, @RequestParam Map<String, Object> params) {
        Apiinfo apiinfo = apiinfoService.getOne(new QueryWrapper<Apiinfo>().eq("code", method));
        if (apiinfo != null) {
            if ("select".equals(apiinfo.getType())) {
                String sql = StrUtil.removeWhere(apiinfo.getScripts());
                String wStr = "";
                JSONArray array = JSONUtil.parseArray(apiinfo.getPdata());
                if (array != null && array.size() > 0) {
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        if (jsonObject.getBool("checked")) {
                            Object temp = params.get(StrUtil.toCamelCase(jsonObject.getStr("columnName")));
                            if (temp != null) {
                                wStr += jsonObject.getStr("columnName") + " = '" + temp.toString() + "' and ";
                            }
                        }
                    }
                }
                if (StrUtil.isNotBlank(wStr)) {
                    sql += " where " + wStr.substring(0, wStr.length() - 4);
                }
                return R.success(jdbcTemplate.queryForList(sql));
            }
        }
        return R.success();
    }

}
