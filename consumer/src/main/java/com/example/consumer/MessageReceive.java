package com.example.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * @author yangzq80@gmail.com
 * @date 2020-07-03
 */
@EnableBinding(Sink.class)
@Slf4j
public class MessageReceive {

    @StreamListener(value = Sink.INPUT)
    public void process(String data) throws InterruptedException {
        log.info("received --- start handle  --- {}", data);
        int i = 0;
        for (; i < 40; i++) {
            Thread.sleep(1000);
            log.info("MessageReceive handle----wait:{}",i);
        }
        log.info("received --- handle {} end --- {}", i, data);
    }

//    @StreamListener(value = Sink.INPUT,condition = "headers['eventType']=='type-1'")
//    public void process2(String data) throws InterruptedException {
//        log.info("received starting handle...{}",data);
//        Thread.sleep(10*1000);
//        log.info("receive end --->{}", data);
//    }
}
