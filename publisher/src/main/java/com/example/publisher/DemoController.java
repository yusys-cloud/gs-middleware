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

    int i = 1;

    @RequestMapping("/")
    public String hello() {

        return "hello:" + i++;
    }
}
