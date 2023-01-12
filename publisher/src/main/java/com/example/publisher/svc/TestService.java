package com.example.publisher.svc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author yangzq80@gmail.com
 * @date 1/9/23
 */
@Service

@Slf4j
public class TestService {
    public String sleep(int intervalSecond) {

        log.info("Start {} sleeping...", intervalSecond);
        int i = 1;
        for (; i < intervalSecond; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("svc handle----{}", i);
        }

        log.info("sleep {} end...", intervalSecond);

        return "svc sleeping " + intervalSecond + " == " + i + " end";
    }
}
