package demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 1. running-location-distribution service
 * 2. REST API receive request from running-location-simulator
 */
@RestController
@EnableBinding(Source.class)
@Slf4j
public class DeliveryInfoSource {

    @Autowired
    private MessageChannel output;

    @RequestMapping(value = "/api/deliveryInfo", method = RequestMethod.POST)
    public void delivery(@RequestBody String deliveryInfo) {
        log.info("distribution service: " + deliveryInfo);
        this.output.send(MessageBuilder.withPayload(deliveryInfo).build());
    }
}
