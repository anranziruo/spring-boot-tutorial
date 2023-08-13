package com.starfly.domain;

import com.alibaba.fastjson.JSON;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        String jsonStr = JSON.toJSONString(this);
        if(jsonStr.length()==0){
            log.error("JsonMessage toString error");
            return null;
        }
        return jsonStr;
    }

    private String message;
    private String topic;
}
