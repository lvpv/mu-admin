package com.lvpb.mu.config;

import com.lvpb.mu.config.properties.ThreadPoolProperties;
import com.lvpb.mu.constant.MuConstant;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 19:30
 * @description 自定义线程池配置
 */

@EnableAsync
@Configuration
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class ThreadPoolConfiguration {
    @Bean
    public ThreadPoolTaskExecutor threadPoolExecutor(ThreadPoolProperties properties) {
        int processors = Runtime.getRuntime().availableProcessors();
        int corePoolSize = Optional.of(properties.getCorePoolSize()).orElse(2 * processors + 1);
        int maxPoolSize = Optional.of(properties.getMaxPoolSize()).orElse(2 * corePoolSize);
        int keepAliveTime = Optional.of(properties.getKeepAliveSecond()).orElse(100);
        int queueCapacity = Optional.of(properties.getQueueCapacity()).orElse(maxPoolSize * 2);
        String threadNamePrefix = Optional.ofNullable(properties.getThreadNamePrefix()).orElse(MuConstant.THREAD_NAME_PREFIX);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setKeepAliveSeconds(keepAliveTime);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //当调度器shutdown被调用时等待当前被调度的任务完成
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }
}
