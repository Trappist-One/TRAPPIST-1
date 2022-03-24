package com.t1.oss.config;

import com.t1.oss.properties.FileServerProperties;
import com.t1.oss.template.FdfsTemplate;
import com.t1.oss.template.S3Template;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * @author Bruce Lee(copy)
 * @date 2021/2/13
 * <p>
 */
@EnableConfigurationProperties(FileServerProperties.class)
@Import({FdfsTemplate.class, S3Template.class})
public class OssAutoConfigure {

}
