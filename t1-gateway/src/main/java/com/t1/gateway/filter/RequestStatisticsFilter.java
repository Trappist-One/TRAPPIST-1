package com.t1.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.t1.gateway.utils.ReactiveAddrUtil;
import com.t1.log.monitor.PointUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 请求统计分析埋点过滤器
 *
 * @author Bruce Lee(copy)
 * @date 2019/10/7
 * <p>
 */
@Component
public class RequestStatisticsFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        Map<String, String> headers = request.getHeaders().toSingleValueMap();
        UserAgent userAgent = UserAgent.parseUserAgentString(headers.get("user-agent"));
        //埋点
        PointUtil.debug("1", "request-statistics",
                "ip=" + ReactiveAddrUtil.getRemoteAddr(request)
                        + "&browser=" + getBrowser(userAgent.getBrowser().name())
                        + "&operatingSystem=" + getOperatingSystem(userAgent.getOperatingSystem().name()));

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private String getBrowser(String browser) {
        if (StrUtil.isNotEmpty(browser)) {
            if (StringUtils.containsIgnoreCase(browser, "CHROME")) {
                return "CHROME";
            } else if (StringUtils.containsIgnoreCase(browser, "FIREFOX")) {
                return "FIREFOX";
            } else if (StringUtils.containsIgnoreCase(browser, "SAFARI")) {
                return "SAFARI";
            } else if (StringUtils.containsIgnoreCase(browser, "EDGE")) {
                return "EDGE";
            }
        }
        return browser;
    }

    private String getOperatingSystem(String operatingSystem) {
        if (StrUtil.isNotEmpty(operatingSystem)) {
            if (operatingSystem.contains("MAC_OS_X")) {
                return "MAC_OS_X";
            } else if (operatingSystem.contains("ANDROID")) {
                return "ANDROID";
            }
        }
        return operatingSystem;
    }
}
