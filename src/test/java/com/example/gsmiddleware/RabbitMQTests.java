package com.example.gsmiddleware;

import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.StrictExceptionHandler;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangzq80@gmail.com
 * @date 11/25/22
 */
public class RabbitMQTests {

    void TestConnect() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setAutomaticRecoveryEnabled(true);  //默认自动重连

        factory.setUsername("admin");
        factory.setPassword("admin");

        List<Address> addresses = new ArrayList();
        addresses.add(new Address("192.168.0.128", 5671));
        addresses.add(new Address("192.168.0.190", 5671));
        addresses.add(new Address("192.168.0.34", 5671));

        Connection connection = factory.newConnection(addresses);

    }
}
