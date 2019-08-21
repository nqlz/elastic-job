package com.zt.elasticstarter.thread;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author kzh
 * @Date 2019/5/25 23:15
 */
@Slf4j
@ConfigurationProperties(prefix = "threadpool.order")
@Component
@Data
public class OrderThreadPoolConf {

    private ExecutorService orderThreadPool;

    private Integer corePoolSize;

    private Integer maxPoolSize;

    private Long keepAliveTime;

    private Integer queueSize;

    @PostConstruct
    public void init() {
        orderThreadPool = new ThreadPoolExecutor(this.corePoolSize, this.maxPoolSize, this.keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<>(this.queueSize), new DefaultThreadFactory("bbs"));
        log.info("---------- 创建订单线程池成功 ------------");
    }

    @PreDestroy
    public void destroy() {
        orderThreadPool.shutdown();
        log.info("---------- 关闭订单线程池 ------------");
    }

    public ExecutorService getInstance() {
        return orderThreadPool;
    }

}
