package com.lvpb.mu.config.properties;

import com.lvpb.mu.constant.MuConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 19:32
 * @description ThreadPoolProperties
 */

@Data
@ConfigurationProperties(prefix = "mu.thread")
public class ThreadPoolProperties {

    /**
     * 核心线程数
     */
    private int corePoolSize = 1;

    /**
     * 最大线程数
     */
    private int maxPoolSize = 2;

    /**
     * 线程空闲时间,单位：秒
     */
    private int keepAliveSecond = 60;

    /**
     * 队列长度
     */
    private int queueCapacity = 20;

    /**
     * 线程池中线程名称前缀
     */
    private String threadNamePrefix = MuConstant.THREAD_NAME_PREFIX;
}
