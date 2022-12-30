package com.example.consumer.rabbit;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangzq80@gmail.com
 * @date 12/28/22
 */
@Configuration
public class RabbitConfig {

//    @Bean
//    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
//        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
//
//        Map<String, Object> arguments = new HashMap<>();
//        arguments.put("x-queue-type", "quorum");
//        Queue queue = new Queue("my-quorum-queue", true, false, false, arguments);
//        rabbitAdmin.declareQueue(queue);
//
//        return rabbitAdmin;
//    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {

        // 创建 RabbitTemplate
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());

        // 启用 publisher confirms
        template.setMandatory(true);

        // 定义回调函数
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("Confirm received");
            } else {
                System.out.println("Confirm lost");
            }
        });

        return template;
    }
}

