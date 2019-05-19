package com.zk.springcloudkafkastream.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaDemo {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        String topic = "topic-kafka";
        Integer partition = 0;
        Long timestamp = System.currentTimeMillis();
        String key = "Key-zhoukun";
        String value = "Value -zhoukun";

        ProducerRecord<String, String> messgInfo = new ProducerRecord(topic, partition, timestamp, key, value);

        producer.send(messgInfo);
        System.out.println("-----------");

    }
}
