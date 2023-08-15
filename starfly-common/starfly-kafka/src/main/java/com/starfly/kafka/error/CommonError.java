package com.starfly.kafka.error;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

@Slf4j
public class CommonError implements CommonErrorHandler {
    @Override
    public void handleRecord(Exception thrownException, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer,
                             MessageListenerContainer container) {
        log.error("消费消息失败！topic: {}, key: {}, value: {},异常:{}", record.topic(),
                record.key(), record.value(), thrownException.getMessage());
        // 消息消费失败,不处理
        TopicPartition topicPartition = new TopicPartition(record.topic(), record.partition());
        //跳过当前消息,继续消费下一条消息
        consumer.seek(topicPartition, record.offset()+1);
    }

    @Override
    public void handleBatch(Exception thrownException, ConsumerRecords<?, ?> data, Consumer<?, ?> consumer, MessageListenerContainer container, Runnable invokeListener) {
        log.error("批量消费消息失败！异常:{}", thrownException.getMessage());
        data.partitions().forEach(topicPartition -> {
            consumer.seek(topicPartition,data.records(topicPartition).get(data.records(topicPartition).size()-1).offset()+1);
        });
    }

    @Override
    public void handleOtherException(Exception thrownException, Consumer<?, ?> consumer, MessageListenerContainer container, boolean batchListenerr) {
        log.error("消费消息失败！异常:{}", thrownException.getMessage());
    }
}
