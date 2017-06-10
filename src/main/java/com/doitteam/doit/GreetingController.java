package com.doitteam.doit;

import com.doitteam.doit.domain.Mensaje;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.ZonedDateTime;

@Controller
public class GreetingController {


    @MessageMapping("/sendMsg")
    @SendTo("/topic/greetings")
    public Mensaje msg(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Mensaje(message, ZonedDateTime.now());
    }



}
