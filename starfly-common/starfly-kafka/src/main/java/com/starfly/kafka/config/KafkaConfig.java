package com.starfly.kafka.config;

import com.starfly.kafka.filter.ConsumerFilter;
import com.starfly.kafka.domain.JsonMessage;
import com.starfly.kafka.filter.ConsumerJsonFilter;
import com.starfly.kafka.listener.RebalancedListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import javax.annotation.Resource;
import java.util.Map;

@Configuration
@Slf4j
public class KafkaConfig {
    @Resource
    private KafkaProperties kafkaProperties;

    // 重试次数
    private static final int RETRY_MAX = 3;


    @Bean
    public ConsumerFactory<Object, Object> consumerFactory() {
        Map<String,Object> properties= kafkaProperties.getConsumer().buildProperties();
        properties.put("key.deserializer", StringDeserializer.class);
        properties.put("value.deserializer", StringDeserializer.class);
        properties.put("allow.auto.create.topics", false);
        properties.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG,"");
        return new DefaultKafkaConsumerFactory<Object, Object>(properties);
    }

    @Bean
    public ProducerFactory<Object, Object> producerFactory() {
        Map<String,Object> properties= kafkaProperties.getProducer().buildProperties();
        properties.put("key.serializer", StringSerializer.class);
        properties.put("value.serializer", StringSerializer.class);
        properties.put("enable.idempotence", false);
        properties.put("retries", RETRY_MAX);
        properties.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG,"");
        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean(name = "farLocationContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Object, Object>>
    farLocationContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<Object, Object>();
        factory.setConsumerFactory(consumerFactory());

        RecordFilterStrategy<Object, Object> filter = new ConsumerFilter();
        factory.setRecordFilterStrategy(filter);
        factory.setBatchListener(true);
//        factory.setCommonErrorHandler(new CommonError());
        ContainerProperties props = factory.getContainerProperties();
        props.setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        props.setConsumerRebalanceListener(new RebalancedListener());
        return factory;
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String,Object> properties= kafkaProperties.getAdmin().buildProperties();
        properties.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG,"");
        return new KafkaAdmin(properties);
    }

    @Bean
    public ConsumerFactory<Object, JsonMessage> consumerJsonFactory() {
        Map<String,Object> properties= kafkaProperties.getConsumer().buildProperties();
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        properties.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        properties.put(JsonDeserializer.VALUE_DEFAULT_TYPE, JsonMessage.class);
        properties.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG,"");
        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @Bean(name = "jsonContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Object, JsonMessage>>
    jsonContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Object, JsonMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<Object, JsonMessage>();
        factory.setConsumerFactory(consumerJsonFactory());

        RecordFilterStrategy<Object, JsonMessage> filter = new ConsumerJsonFilter();
        factory.setRecordFilterStrategy(filter);
        factory.setBatchListener(true);
//        factory.setCommonErrorHandler(new CommonError());
        ContainerProperties props = factory.getContainerProperties();
        props.setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        props.setConsumerRebalanceListener(new RebalancedListener());
        return factory;
    }
}
