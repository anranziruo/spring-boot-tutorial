package com.starfly.runner;

import com.starfly.service.CommonConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@Component
@Slf4j
public class CommonRun implements CommandLineRunner {
    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private CommonConsumerService commonConsumerService;

    @Resource
    private ConfigurableEnvironment configurableEnvironment;

    @Resource
    private KafkaAdmin kafkaAdmin;

    @Override
    public void run(String... args) throws Exception {

        String currentRunnerName = applicationContext.getBean(this.getClass()).getClass().getSimpleName();
        log.info("currentRunnerName = {}", currentRunnerName);
        String runEnv = configurableEnvironment.getProperty("spring.config.activate.on-profile");
        log.info("runEnv = {}", runEnv);

        log.info("{} start,输入参数是{}",currentRunnerName, Arrays.toString(args));
        if (args.length <= 1) {
            log.error("{} start error,缺少调度器参数",currentRunnerName);
            return;
        }
        if(!currentRunnerName.equals(args[0])){
            log.info("{} start error,调度器参数不匹配",currentRunnerName);
            return;
        }

        String kafkaTopic = args[1].trim();
        String kafkaGroup = args[1].trim();
        log.info("kafkaTopic = {}, kafkaGroup = {}", kafkaTopic, kafkaGroup);


        AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());

        //判断输入的topic是否存在
        List<String> topicList = new ArrayList<>();
        topicList.add(kafkaTopic);

        AtomicBoolean isTopicExist = new AtomicBoolean(true);
        DescribeTopicsResult describeTopicsResult = adminClient.describeTopics(topicList);
        describeTopicsResult.topicNameValues().forEach((k,v)->{
            try {
                log.info("topic详情:{}",v.get());
            } catch (Exception e) {
                log.error("kafkaTopic = {} not exist,异常:{}", kafkaTopic,e.getMessage());
                isTopicExist.set(false);
            }
        });
        if(!isTopicExist.get()){
            log.info("{} start error,topic不存在",currentRunnerName);
            adminClient.close();
            return;
        }

        adminClient.close();
        commonConsumerService.startCommonConsumer(kafkaTopic,kafkaGroup);
    }
}
