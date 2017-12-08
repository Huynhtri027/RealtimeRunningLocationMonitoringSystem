package demo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.model.PaymentInfo;
import demo.service.PaymentInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
public class PaymentServiceRestController {

    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(path = "/api/payments", method = RequestMethod.POST)
    public void payments(@RequestBody String paymentInfo) throws IOException {
        log.info("Payment at payment-service: " + paymentInfo);
        PaymentInfo payment = this.objectMapper.readValue(paymentInfo, PaymentInfo.class);
        log.info("Payment convert at payment-service: " + payment);
        paymentInfoService.processPaymentInfo(payment);
    }
}
