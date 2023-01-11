package com.example.shutdown;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author yangzq80@gmail.com
 * @date 1/9/23
 */

@Slf4j
public class TestGracefulShutdown {

    @Test
    public void testShutdown() {
        RestApis.shutdown();
    }

    @Test
    public void testRequest() {
        RestApis.requestSleep(20);
    }

    @Test
    public void testConsumer() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RestApis.sendMessage(20);
            }
        }).start();
        Thread.sleep(500);
        new Thread(new Runnable() {
            @Override
            public void run() {
                RestApis.sendMessage(20);
            }
        }).start();
        Thread.sleep(1000);
        RestApis.shutdownConsumer();
    }

    //消息持续发送期间停机,消息可继续发送，停机瞬间不关闭channel
    @Test
    public void testSendMessage() throws InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                RestApis.sendMessage(3);
            }
        }).start();

        Thread.sleep(1000);

        testShutdownTimeout();
    }


    //停机超时时间内业务正常执行完成，超时时间外异常中止
    @Test
    public void testShutdownTimeout() {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                RestApis.requestSleep(20);
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                RestApis.shutdown();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Both threads have finished");
    }

    //停机期间 http 不再接受请求
    @Test
    public void testShutdownPeriodResponse() {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                RestApis.requestSleep(35);
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                RestApis.shutdown();
            }
        });


        t1.start();
        t2.start();

        for (int i = 1; i < 4; i++) {
            try {
                RestApis.requestSleep(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Both threads have finished");
    }
}