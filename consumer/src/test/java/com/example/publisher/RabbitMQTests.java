package com.example.publisher;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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
        factory.setNetworkRecoveryInterval(5000);

        factory.setUsername("admin");
        factory.setPassword("admin");

        List<Address> addresses = new ArrayList();
        addresses.add(new Address("192.168.0.128", 5671));
        addresses.add(new Address("192.168.0.190", 5671));
        addresses.add(new Address("192.168.0.34", 5671));

        Connection conn = factory.newConnection(addresses);

        Channel channel = conn.createChannel();
    }
}
