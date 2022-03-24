package com.t1.biz.activiti.config;

import com.t1.biz.activiti.diagramgenerate.processPicture.CustomProcessDiagramGeneratorService;
import lombok.AllArgsConstructor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author Bruce Lee ( copy )
 * @date 2020/4/4
 * @description activiti 配置
 */
@Configuration
@AllArgsConstructor
public class ActivitiConfig {
    private final DataSource dataSource;
    private final PlatformTransactionManager transactionManager;
    private final CustomProcessDiagramGeneratorService customProcessDiagramGeneratorService;

    @Bean
    public SpringProcessEngineConfiguration getProcessEngineConfiguration() {
        SpringProcessEngineConfiguration config =
                new SpringProcessEngineConfiguration();

        // 流程图字体设置
        config.setActivityFontName("华文细黑");
        config.setAnnotationFontName("华文细黑");
        config.setLabelFontName("华文细黑");

        //启用异步执行器
        config.setAsyncExecutorEnabled(true);
        //激活异步执行器线程池(Activiti启动创建线程池)
        config.setAsyncExecutorActivate(true);
        //激活作业执行器(让定时器工作)
        config.setJobExecutorActivate(true);

        config.setDataSource(dataSource);
        config.setTransactionManager(transactionManager);
        config.setDatabaseType("mysql");
        config.setDatabaseSchemaUpdate("true");

        //邮箱配置
        config.setMailServerHost("smtp.qq.com");
        config.setMailServerPort(465);
        config.setMailServerDefaultFrom("1827945911@qq.com");
        config.setMailServerUsername("1827945911@qq.com");
        config.setMailServerPassword("skvhlkhrartadjgc");
        config.setMailServerUseSSL(true);

        //自定义流程图样式
        config.setProcessDiagramGenerator(customProcessDiagramGeneratorService);

        return config;
    }

    @Bean
    @Primary
    public TaskExecutor primaryTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}
