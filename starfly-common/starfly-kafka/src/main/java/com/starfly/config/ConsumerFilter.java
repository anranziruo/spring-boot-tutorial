package com.starfly.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;

import java.util.List;

public class ConsumerFilter implements RecordFilterStrategy<Object, Object> {
    @Override
    public boolean filter(ConsumerRecord<Object, Object> consumerRecord) {
        return false;
    }

    @Override
    public List<ConsumerRecord<Object, Object>> filterBatch(List<ConsumerRecord<Object, Object>> consumerRecords) {
        return RecordFilterStrategy.super.filterBatch(consumerRecords);
    }
}
