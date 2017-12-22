package demo;


import com.fasterxml.jackson.databind.ObjectMapper;
import demo.model.CurrentPosition;
import demo.model.FitbitInfo;
import demo.service.FitbitInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.IOException;

@EnableBinding(Sink.class)
@Slf4j
@MessageEndpoint
public class RunningLocationUpdaterSink {
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private FitbitInfoService fitbitInfoService;

    @Autowired
    private ObjectMapper objectMapper;

    @ServiceActivator(inputChannel = Sink.INPUT)
    public void updateLocation(String input) throws IOException {
        log.info("Location input in updater: " + input);
        CurrentPosition position = this.objectMapper.readValue(input, CurrentPosition.class);
        log.info("Position in updater: " + position);
        this.template.convertAndSend("/topic/locations", position);

        if (position.getRunnerStatus().isFinished()) {
            FitbitInfo fitbitInfo = new FitbitInfo(position.getRunningId(), position.getRunnerStatus().getDistance(),
                    position.getRunnerStatus().getStartDate(), position.getRunnerStatus().getStartTime(), position.getRunnerStatus().isUpload());

            FitbitInfo info = this.fitbitInfoService.saveFitbitInfo(fitbitInfo);

            log.info("mongoDB save successfully: " + info.toString());
        }
    }
}
