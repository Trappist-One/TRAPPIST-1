package com.t1.gateway.filter;

import com.t1.common.constant.CommonConstants;
import com.t1.common.constant.ConfigConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 示例
 *
 * @author jarvis create by 2022/5/8
 */
@Component
public class VersionLbIsolationFilter implements GlobalFilter, Ordered {

    @Value("${"+ ConfigConstants.CONFIG_LOADBALANCE_ISOLATION_ENABLE+":}")
    private Boolean enableVersionControl;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if(Boolean.TRUE.equals(enableVersionControl)
                && exchange.getRequest().getQueryParams().containsKey(CommonConstants.T1_VERSION)){
            String version = exchange.getRequest().getQueryParams().get(CommonConstants.T1_VERSION).get(0);
            ServerHttpRequest rebuildRequest = exchange.getRequest().mutate().headers(header -> {
                header.add(CommonConstants.T1_VERSION, version);
            }).build();
            ServerWebExchange rebuildServerWebExchange = exchange.mutate().request(rebuildRequest).build();
            return chain.filter(rebuildServerWebExchange);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
