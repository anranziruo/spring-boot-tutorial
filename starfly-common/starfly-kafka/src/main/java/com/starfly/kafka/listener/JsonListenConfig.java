package com.starfly.kafka.listener;

import com.starfly.kafka.service.CommonProducerService;
import com.starfly.kafka.domain.JsonMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
public class JsonListenConfig extends KafkaProperties.Listener{

    private final String id;

    private final String topic;

    private  final  String groupId;

    private final Integer concurrency;

    private final String[] properties;

    private final String containerFactory;

    private static final String EMPTY_JSON = "{}";


    @Resource
    private CommonProducerService commonProducerService;



    public JsonListenConfig(String id, String topic,String groupId,Integer concurrency,
                              String[] properties,String containerFactory){
        this.id = id;
        this.topic = topic;
        this.groupId = groupId;
        this.concurrency = concurrency;
        this.properties = properties;
        this.containerFactory = containerFactory;
    }

    public String getId() {
        return this.id;
    }

    public String getTopic() {
        return this.topic;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public Integer getConcurrency() {
        return this.concurrency;
    }

    public String[] getProperties() {
        return this.properties;
    }

    public  String getContainerFactory(){
        return this.containerFactory;
    }


    @KafkaListener(id = "#{__listener.id}", topics = "#{__listener.topic}",
            concurrency = "#{__listener.concurrency}",groupId = "#{__listener.groupId}",
            properties = "#{__listener.properties}",containerFactory = "#{__listener.containerFactory}")
    public void listen(@Payload List<JsonMessage> messages, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                       @Header(KafkaHeaders.OFFSET) List<Long> offsets,
                       @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys, Acknowledgment ack, Consumer consumer) {
        String sendTopic = getTopic();
        for (JsonMessage message : messages) {
            try {
                if (message != null && !message.toString().equals(EMPTY_JSON)) {
//                    commonProducerService.send(sendTopic, keys.get(i), messages.get(i));
                    log.info("生产者发送数据成功:{},消息：{}", sendTopic, message);
                }
                else {
                    //打印原始数据
                    log.error("非法数据格式{},{}", sendTopic, message);
                }
            } catch (Exception e) {
                log.error("生产者发送数据失败:{},消息：{}", e.getMessage(), message);
            } finally {
                ack.acknowledge();
            }
        }
    }
}
