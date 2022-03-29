package com.t1.oauth.util;

import cn.hutool.extra.servlet.ServletUtil;
import com.t1.common.utils.AddressUtil;
import com.t1.common.utils.DateUtil;
import com.t1.common.utils.StrUtil;
import lombok.experimental.UtilityClass;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author Bruce Lee ( copy )
 * @date 2020/3/19
 * @description 登录日志 util
 */
@UtilityClass
public class LoginLogUtil {

    public PreparedStatementSetter setLoginLog(HttpServletRequest request, String loginType, String userName, String errorMsg){
//        String ip = ServletUtil.getClientIP(request);
        String ip = getIPAddress(request);
        return new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, userName);
                ps.setString(2, loginType);
                ps.setString(3, ip);
                ps.setString(4, AddressUtil.getCityInfo(ip));
                ps.setString(5, request.getHeader("user-agent"));
                if (StrUtil.isNotBlank(errorMsg)) {
                    ps.setString(6, "1");
                    ps.setString(7, errorMsg);
                } else {
                    ps.setString(6, "0");
                    ps.setString(7, "");
                }
                ps.setString(8, DateUtil.now());
            }
        };
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public static String getIPAddress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }
}
