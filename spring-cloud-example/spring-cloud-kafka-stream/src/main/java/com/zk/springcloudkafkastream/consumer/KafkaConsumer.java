package com.zk.springcloudkafkastream.consumer;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Kafka消费组
 *
 * @author zhoukun
 */
@Service
public class KafkaConsumer {

    /**
     * 基于注解模式接受kafka消息
     * @param msg
     */
    @KafkaListener(topics = "topic-kafka")
    public void consumer(String msg) {
        System.out.println("消费到：msg=" + msg);
    }

}
