package demo.service.impl;

import demo.model.PaymentInfo;
import demo.model.PaymentInfoRepository;
import demo.service.PaymentInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
@Slf4j
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    // Sent REST request from backend
    //@Autowired
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${com.zeyu.order.service}")
    String orderService;

    @Override
    public void processPaymentInfo(PaymentInfo payment) {
        payment.setPaymentComplete(true);
        payment.setPaymentTimestamp(new Date());
        paymentInfoRepository.save(payment);

        log.info("Payment complete info send to order-service" + payment);
        this.restTemplate.postForLocation(orderService + "/api/paymentsCompleteInfo", payment);
    }
}
