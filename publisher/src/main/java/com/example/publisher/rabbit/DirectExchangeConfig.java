package com.example.publisher.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangzq80@gmail.com
 * @date 12/29/22
 * <p>
 * Exchange type	Default pre-declared names
 * Direct exchange	(Empty string) and amq.direct
 * Fanout exchange	amq.fanout
 * Topic exchange	amq.topic
 * Headers exchange	amq.match (and amq.headers in RabbitMQ)
 */
@Configuration
public class DirectExchangeConfig {

    //不绑定Exchange 默认使用Queue名称为RoutingKey
    @Bean
    public Queue TestQuorumQueue() {
        return declareQuorumQueue("test-quorum-queue");
    }

    @Bean
    public Queue TestDirectQueue() {

        return declareQuorumQueue("test-direct-exchange-queue");
    }

    @Bean
    public DirectExchange TestDirectExchange() {
        return new DirectExchange("DirectExchange");
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with("test-direct-exchange-queue");
    }

    private Queue declareQuorumQueue(String name) {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-queue-type", "quorum");
        return new Queue(name, true, false, false, arguments);
    }

}