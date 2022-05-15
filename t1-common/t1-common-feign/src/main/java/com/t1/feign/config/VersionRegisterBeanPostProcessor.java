package com.t1.feign.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.common.utils.StringUtils;
import com.t1.common.constant.CommonConstants;
import com.t1.common.constant.ConfigConstants;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 将版本注册到注册中心的组件
 *
 * @author jarvis create by 2022/3/20
 */
public class VersionRegisterBeanPostProcessor  implements BeanPostProcessor {
    @Value("${"+ ConfigConstants.CONFIG_LOADBALANCE_VERSION+":}")
    private String version;
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof NacosDiscoveryProperties && StringUtils.isNotBlank(version)){
            NacosDiscoveryProperties nacosDiscoveryProperties = (NacosDiscoveryProperties) bean;
            nacosDiscoveryProperties.getMetadata().putIfAbsent(CommonConstants.METADATA_VERSION, version);
        }
        return bean;
    }
}
