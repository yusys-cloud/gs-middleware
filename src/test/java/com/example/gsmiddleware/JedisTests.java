package com.example.gsmiddleware;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @author yangzq80@gmail.com
 * @date 11/18/22
 */
public class JedisTests {
    @Test
    void TestCluster() {
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        //任意一个即可获取集群网络拓扑节点
        jedisClusterNodes.add(new HostAndPort("192.168.0.169", 7010));
        jedisClusterNodes.add(new HostAndPort("192.168.0.169", 7020));
        jedisClusterNodes.add(new HostAndPort("192.168.0.169", 7030));
        JedisCluster jedis = new JedisCluster(jedisClusterNodes);

        int i = 0;
        while (true) {
            try {
                jedis.set("uid", "id" + i);

                System.out.println(jedis.get("uid"));

                Thread.sleep(1000);
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
