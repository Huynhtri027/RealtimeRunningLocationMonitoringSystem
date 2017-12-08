package demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;

@EnableBinding(Sink.class)
@Slf4j
public class RestaurantDeliverySink {

//    @Autowired
//    private SimpMessagingTemplate template;

//    @Autowired
//    private ObjectMapper objectMapper;

    @ServiceActivator(inputChannel = Sink.INPUT)
    public void updateDelivery(String input) {
        log.info("DeliveryInfo input in delivery service " + input);
//        CurrentPosition position = this.objectMapper.readValue(input, CurrentPosition.class);
//        this.template.convertAndSend("/topic/locations", position);
    }
}
