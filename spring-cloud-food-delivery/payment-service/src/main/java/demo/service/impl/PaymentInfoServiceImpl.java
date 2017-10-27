package demo.service.impl;

import demo.model.PaymentInfo;
import demo.service.PaymentInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class PaymentInfoServiceImpl implements PaymentInfoService {

    // Sent REST request from backend
    //@Autowired
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${com.zeyu.order.service}")
    String orderService;

    @Override
    public void processPaymentInfo(PaymentInfo payment) {
        payment.setPaymentComplete(true);
        log.info("Payment complete info to order-service" + payment);
        this.restTemplate.postForLocation(orderService + "/api/paymentsCompleteInfo", payment);
    }
}
