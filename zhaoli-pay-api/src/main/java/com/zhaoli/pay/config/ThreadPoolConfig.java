package com.zhaoli.pay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * springboot 整合线程池
 */
/**
 *  它启用了Spring的异步方法执行功能。
 *  proxyTargetClass = true 是一个可选的属性，用于指示是否使用CGLIB代理来创建异步方法的代理对象。
 *  当设置为 true 时，Spring将使用CGLIB动态代理来创建代理对象，这可以处理更复杂的场景，例如代理类而不仅仅是接口。
 *  当设置为 false 时，Spring将使用JDK动态代理。
 */
@EnableAsync
@Configuration
public class ThreadPoolConfig {

    /**
     * 线程核心数
     */
    @Value("${zhaoli.pay.thread.corePoolSize}")
    private int corePoolSize;
    /**
     * 线程最大数
     */
    @Value("${zhaoli.pay.thread.maxPoolSize}")
    private int maxPoolSize;
    /**
     * 任务容量
     */
    @Value("${zhaoli.pay.thread.queueCapacity}")
    private int queueCapacity;
    /**
     * 允许空闲时间，默认60
     */
    @Value("${zhaoli.pay.thread.keepAlive}")
    private int keepAlive;

    /***
     * 核心线程数 设定2
     * @return
     */
    @Bean
    public TaskExecutor newSaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAlive);
        threadPoolTaskExecutor.setThreadNamePrefix("PayThread-");//线程池名称
        //设置拒绝策略 当线程数达到最大时，如何处理新任务
        //CallerRunsPolicy 不由线程池中线程执行，由调用者所在线程执行
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        return threadPoolTaskExecutor;
    }
}