package com.starfly.kafka.service;


import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;


@Service
@Slf4j
public class CommonProducerService {
    @Resource
    KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic,String key, String message) {
        var future = kafkaTemplate.send(topic, key, message);
        future.addCallback(success -> {
            //数据量大,不做处理
        }, fail -> {
            log.error("KafkaMessageProducer 发送消息失败！topic: {}, key: {}, value: {},报错：{}", topic, key, message,
                    fail.getMessage());
        });
    }
}
