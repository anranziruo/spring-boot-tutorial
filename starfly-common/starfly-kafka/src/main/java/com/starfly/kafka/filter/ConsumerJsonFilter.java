package com.starfly.kafka.filter;

import com.starfly.kafka.domain.JsonMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;

import java.util.List;

public class ConsumerJsonFilter implements RecordFilterStrategy<Object, JsonMessage> {
    @Override
    public boolean filter(ConsumerRecord<Object, JsonMessage> consumerRecord) {
        return false;
    }

    @Override
    public List<ConsumerRecord<Object, JsonMessage>> filterBatch(List<ConsumerRecord<Object, JsonMessage>> consumerRecords) {
        return RecordFilterStrategy.super.filterBatch(consumerRecords);
    }
}
