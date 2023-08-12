package com.starfly.domain;

import com.alibaba.fastjson.JSON;

public class JsonMessage {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    private String message;
    private String topic;
}
