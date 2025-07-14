package org.example.ubersocketserver.controller;


import org.apache.logging.log4j.message.SimpleMessage;
import org.example.ubersocketserver.dto.TestRequest;
import org.example.ubersocketserver.dto.TestRespone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final MessageSendingOperations<String> messagingTemplate;

    public TestController(MessageSendingOperations<String> messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    @MessageMapping("/ping")
    @SendTo("/topic/ping")
    public TestRespone pingCheck(TestRequest message){
        System.out.println("Received message from the Client: " +message.getData());
        return  new TestRespone("Received");
    }


    int i =0;
    @Scheduled(fixedDelay = 2000)
    public void sendPerodicMessage(){
        System.out.println("This is working " + i++);
        messagingTemplate.convertAndSend("/topic/scheduled" ,"perodic Message"+ System.currentTimeMillis());
    }
}
