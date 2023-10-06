package com.esoft.auth.component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    @RabbitListener(queues = "auth")
    public void receiveMessage(String message){
        System.out.println("teesst"+message);
    }
}
