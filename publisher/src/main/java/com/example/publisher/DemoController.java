package com.example.publisher;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangzq80@gmail.com
 * @date 12/28/22
 */
@RestController
public class DemoController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    int i = 1;

    @RequestMapping("/")
    public String hello() {

        return "hello:" + i++;
    }


    @RequestMapping("/quorum")
    public String quorum() {

        //CorrelationData 表示与消息相关的关联数据,可用于消息发送确认中使用
        CorrelationData correlationData = new CorrelationData("msgID-" + i);

        String msg = "default exchange quorum msg:" + i++;

        rabbitTemplate.convertAndSend("test-quorum-queue", (Object) msg, correlationData);

        return msg;
    }

    @RequestMapping("/direct")
    public String direct() {

        String msg = "direct exchange msg:" + i++;

        rabbitTemplate.convertAndSend("DirectExchange", "test-direct-exchange-queue", msg);

        return msg;
    }
}
