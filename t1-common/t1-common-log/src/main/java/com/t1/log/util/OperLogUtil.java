package com.t1.log.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import com.t1.common.utils.AddressUtil;
import com.t1.common.utils.DateUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author Bruce Lee ( copy )
 * @date 2020/3/18
 * @description 操作日志util
 */
@Slf4j
@UtilityClass
public class OperLogUtil {

    public PreparedStatementSetter setOperLog(String title, long time, String userName, String clientId, String errorMsg) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = ServletUtil.getClientIP(request);
        return new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, "1");
                ps.setString(2, title);
                ps.setString(3, request.getMethod());
                ps.setString(4, request.getHeader("user-agent"));
                ps.setString(5, userName);
                ps.setString(6, clientId);
                ps.setString(7, URLUtil.getPath(request.getRequestURI()));
                ps.setString(8, ip);
                ps.setString(9, AddressUtil.getCityInfo(ip));
                ps.setString(10, HttpUtil.toParams(request.getParameterMap()));
                if (StrUtil.isNotBlank(errorMsg)) {
                    ps.setString(11, "1");
                    ps.setString(12, errorMsg);
                } else {
                    ps.setString(11, "0");
                    ps.setString(12, "");
                }
                ps.setString(13, time + "");
                ps.setString(14, DateUtil.now());
            }
        };
    }

}
