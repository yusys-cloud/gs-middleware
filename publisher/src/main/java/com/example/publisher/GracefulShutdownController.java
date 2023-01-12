package com.example.publisher;

import com.example.publisher.svc.MessgeSend;
import com.example.publisher.svc.TestService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangzq80@gmail.com
 * @date 1/10/23
 */
@RestController
public class GracefulShutdownController {

    @Autowired
    TestService testService;

    @Autowired
    MessgeSend messgeSend;

    @RequestMapping("/sleep")
    public String sleep(int t) {

        return testService.sleep(t);
    }

    @RequestMapping("/stream/send")
    public String stream(int n) throws InterruptedException {

        for (int i = 0; i < n; i++) {

            messgeSend.send();
            Thread.sleep(1000);
        }
        return " --- 1s interval --- success --- send number:" + n;
    }

}
