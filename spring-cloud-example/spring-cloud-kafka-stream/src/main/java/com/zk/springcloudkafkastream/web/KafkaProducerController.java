package com.zk.springcloudkafkastream.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaProducerController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("/kafka/send")
    public boolean sendMessage(@RequestParam("meaasge") String meaasge) {
        kafkaTemplate.send("topic-kafka", meaasge);
        return true;
    }
}
