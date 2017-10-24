package demo.controller;

import demo.model.CurrentPositionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class WebSocketController {

    //relay
    @MessageMapping("/sendMessage")
    @SendTo("/topic/locations")
    public CurrentPositionDto sendMessage(CurrentPositionDto message) {
        log.info("Receive message" + message);
        return message;
    }
}
