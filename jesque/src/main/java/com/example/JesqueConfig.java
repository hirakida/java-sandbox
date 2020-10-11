package com.example;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import net.greghaines.jesque.Config;
import net.greghaines.jesque.ConfigBuilder;
import net.greghaines.jesque.Job;
import net.greghaines.jesque.client.Client;
import net.greghaines.jesque.client.ClientPoolImpl;
import net.greghaines.jesque.utils.PoolUtils;
import net.greghaines.jesque.worker.DefaultExceptionHandler;
import net.greghaines.jesque.worker.JobExecutor;
import net.greghaines.jesque.worker.MapBasedJobFactory;
import net.greghaines.jesque.worker.RecoveryStrategy;
import net.greghaines.jesque.worker.Worker;
import net.greghaines.jesque.worker.WorkerEvent;
import net.greghaines.jesque.worker.WorkerImpl;
import net.greghaines.jesque.worker.WorkerListener;

import com.example.jobs.Job1;
import com.example.jobs.Job2;
import com.example.jobs.Job3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JesqueConfig {
    public static final String QUEUE_NAME = "queue1";

    @PostConstruct
    public void init() {
        taskExecutor().execute(worker());
    }

    @PreDestroy
    public void close() {
        worker().end(true);
    }

    @Bean(destroyMethod = "end")
    public Client client() {
        GenericObjectPoolConfig<Jedis> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxIdle(8);
        poolConfig.setMinIdle(0);
        poolConfig.setMaxTotal(8);
        Pool<Jedis> jedisPool = PoolUtils.createJedisPool(createConfig(), poolConfig);
        return new ClientPoolImpl(createConfig(), jedisPool);
    }

    @Bean
    public Worker worker() {
        Map<String, Class<? extends Runnable>> entries = Map.of(Job1.class.getSimpleName(), Job1.class,
                                                                Job2.class.getSimpleName(), Job2.class,
                                                                Job3.class.getSimpleName(), Job3.class);
        MapBasedJobFactory jobFactory = new MapBasedJobFactory(entries);

        Worker worker = new WorkerImpl(createConfig(), List.of(QUEUE_NAME), jobFactory);
        worker.getWorkerEventEmitter().addListener(new WorkerListenerImpl());
        worker.setExceptionHandler(new WorkerExceptionHandler());
        return worker;
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.initialize();
        return executor;
    }

    private static Config createConfig() {
        return new ConfigBuilder()
                .withHost("localhost")
                .withPort(6379)
                .build();
    }

    private static class WorkerExceptionHandler extends DefaultExceptionHandler {
        @Override
        public RecoveryStrategy onException(JobExecutor jobExecutor, Exception exception, String curQueue) {
            log.error("onException {} {} {}", jobExecutor, exception, curQueue);
            return super.onException(jobExecutor, exception, curQueue);
        }
    }

    private static class WorkerListenerImpl implements WorkerListener {
        @Override
        public void onEvent(WorkerEvent event, Worker worker, String queue, Job job, Object runner,
                            Object result, Throwable t) {
            if (event == WorkerEvent.JOB_FAILURE || event == WorkerEvent.WORKER_ERROR) {
                log.error("event={} worker={} queue={} job={} runner={} result={}",
                          event, worker, queue, job, runner, result, t);
            }
        }
    }
}
