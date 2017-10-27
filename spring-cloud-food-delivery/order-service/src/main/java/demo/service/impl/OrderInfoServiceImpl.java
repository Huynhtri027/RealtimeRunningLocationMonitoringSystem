package demo.service.impl;

import demo.model.OrderInfo;
import demo.model.OrderInfoRepository;
import demo.model.PaymentInfo;
import demo.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    // Sent REST request from backend
    //@Autowired
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${com.zeyu.payment.service}")
    String paymentService;

    @Value("${com.zeyu.distribution.service}")
    String distributionService;

    @Override
    public OrderInfo saveOrderInfo(OrderInfo orderInfo) {
        log.info("Service: " + orderInfo);
        return orderInfoRepository.save(orderInfo);
    }

    public void processPaymentInfo(PaymentInfo paymentInfo) {
        log.info("Payment info is sending to Payment Service: " + paymentInfo);
        this.restTemplate.postForLocation(paymentService + "/api/payments", paymentInfo);
    }

    public void processDeliveryInfo(String deliveryInfo) {
        log.info("Delivery info is sending to Distribution Service: " + deliveryInfo);
        this.restTemplate.postForLocation(distributionService + "/api/deliveryInfo", deliveryInfo);
    }
}
