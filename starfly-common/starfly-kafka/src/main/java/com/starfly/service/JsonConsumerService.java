package com.starfly.service;

import com.starfly.listener.JsonListenConfig;
import com.starfly.listener.StringListenConfig;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class JsonConsumerService {
    @Resource
    private ApplicationContext applicationContext;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    JsonListenConfig initJsonListenConfig(String topic, String id, String group, Integer concurrency, String[] properties,
                                              String containerFactory) {
        return new JsonListenConfig(id, topic,group,concurrency,properties,containerFactory);
    }

    public void startJsonConsumer(String topic, String group) {

        //增加sleep时间
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //设置消费者的并发数
        int concurrency = 3;
        // 设置消费者的id
        String id = java.util.UUID.randomUUID().toString();

        // 设置消费者的属性
        List<String> propertiesList = new java.util.ArrayList<>();
        propertiesList.add("max.poll.records=100");
        propertiesList.add("auto.offset.reset=latest");
        propertiesList.add("enable.auto.commit=false");
        //每调用一次，就提交一次offset
        String[] properties = new String[propertiesList.size()];
        propertiesList.toArray(properties);

        applicationContext.getBean(JsonListenConfig.class, topic, id,group,concurrency,
                properties,"jsonContainerFactory");
    }
}
