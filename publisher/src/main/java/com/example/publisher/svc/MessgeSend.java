package com.example.publisher.svc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author yangzq80@gmail.com
 * @date 2020-07-03
 */
@Service
//@EnableScheduling
@EnableBinding(Source.class)
@Slf4j
public class MessgeSend {

    //private final EmitterProcessor<Message<?>> processor = EmitterProcessor.create();

    @Autowired
    Source source;

    int i = 0;

    //    @Scheduled(fixedRate=10000)
    public void send() {
        i++;
        log.info("message....." + i);

        Message message = MessageBuilder.withPayload("message-hello-" + i).
                setHeader("eventType", "type-" + i % 2).build();

        log.info(message.getHeaders().toString());

        source.output().send(message);
    }
}
