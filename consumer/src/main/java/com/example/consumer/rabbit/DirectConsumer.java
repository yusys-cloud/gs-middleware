package com.example.consumer.rabbit;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author yangzq80@gmail.com
 * @date 12/28/22
 */
@Service
@Slf4j
public class DirectConsumer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "test-quorum-queue")
    public void processQuorum(String content, Message message, Channel channel) {

        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            log.info("Received --- DeliveryTag:{} --- Content: {}", deliveryTag, content);
            //TODO business codes
            //int i = 1 / 0;

            //业务逻辑完成，消息ack确认，队列中删除消息
            channel.basicAck(deliveryTag, false);

            log.info("finished ack message requeue false");
        } catch (Exception e) {
            try {
                //业务逻辑异常，消息不进行ack确认，重新放回队列再次投递
                channel.basicNack(deliveryTag, false, true);

                log.error("nack message requeue true:{}", e.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //    @RabbitListener(queues = "test-quorum-queue")
    public void listen(String in) {
        log.info("Received --- {}", in);
    }

    @RabbitListener(queues = "test-direct-exchange-queue")
    public void testDirct(String in, Message message, Channel channel) throws Exception {
        log.info("Received --- {}", in);
        Thread.sleep(10000);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}