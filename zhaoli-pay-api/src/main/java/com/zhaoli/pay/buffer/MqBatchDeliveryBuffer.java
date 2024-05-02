package com.zhaoli.pay.buffer;

/**
 * @author 赵立
 * @qq 3083645195
 * @weixin 17691086307
 */
import com.alibaba.fastjson.JSONObject;
import com.zhaoli.pay.config.RabbitMQBatchConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 缓冲区
 * 将缓冲区中的 msg 消息批量投递到 mq 中
 */
@Component
@Slf4j
public class MqBatchDeliveryBuffer<E> {
    @Autowired
    private AmqpTemplate amqpTemplate;
    /**
     * 是否继续运行
     * 关键字 volatile 用于修饰变量，它的作用是确保多个线程之间对变量的可见性
     * 当一个变量被声明为 volatile 时，线程在修改变量时会立即将修改后的值刷新到主内存中，
     * 并且其他线程在读取该变量时会直接从主内存中读取最新的值，而不是使用本地缓存的值
     */
    private volatile boolean isRun=true;
    /**
     * 定义容器存放 msg
     */
    private LinkedBlockingQueue<E> buffers = new LinkedBlockingQueue<>();

//    public MqBatchDeliveryBuffer() {
//        //相等于 new TaskBufferThread().start();
//        //利用单例线程池启动该线程
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.execute(new BatchBufferThread());
//    }

    /**
     * 将 msg 消息暂时存放到缓冲区中
     */
    public void add(E e) {
        buffers.offer(e);
    }

    /**
     * 合并消息线程(将多条消息合并成一条msg，投递到 mq 中)
     */
    class BatchBufferThread implements Runnable {
        @Override
        public void run() {
            while (isRun) {
                try {
                    ArrayList<E> tempBufferMsgs = new ArrayList<>();
                    for (int i = 0; i < RabbitMQBatchConfig.BUFFER_SIZE; i++) {
//                        E e = buffers.take();//从队列中获取 msg
                        //超时时间为1s 1s没有取到的情况下返回null
                        E e = buffers.poll(RabbitMQBatchConfig.BUFFER_INTERVAL_TIME, TimeUnit.SECONDS);
                        if (e!=null){
                            tempBufferMsgs.add(e);//存入临时容器中
                        }
                    }
                    //批量形式将数据投递到MQ中
                    String sendMsgs = JSONObject.toJSONString(tempBufferMsgs);
                    log.info("[生产者批量的方式获取数据，投递到MQ中,sendMsgs:{}]", sendMsgs);
                    amqpTemplate.convertAndSend(RabbitMQBatchConfig.EXCHANGE_BATCH_MAYIKT_NAME, "", sendMsgs);
                } catch (Exception e) {
                    log.error("<e;{}>", e);
                }
            }
        }
    }

}
