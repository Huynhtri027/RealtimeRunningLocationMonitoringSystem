package demo.rest;

import demo.model.OrderInfo;
import demo.model.PaymentInfo;
import demo.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class OrderRestController {
    @Autowired
    private OrderInfoService orderInfoService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String helloWorld() {
        return "Hello Order Service";
    }

    @RequestMapping(value = "/orderInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestBody OrderInfo orderInfo) {
        log.info("rest: " + orderInfo);
        orderInfoService.saveOrderInfo(orderInfo);

        PaymentInfo paymentInfo = new PaymentInfo(orderInfo.getOrderId(), orderInfo.getTotalPrice(), orderInfo.getCardNum(),
                orderInfo.getExpirationMonth(), orderInfo.getExpirationYear(),
                orderInfo.getSecurityCode(), orderInfo.isPaymentComplete());
        orderInfoService.processPaymentInfo(paymentInfo);
    }

    @RequestMapping(path = "/api/paymentsCompleteInfo", method = RequestMethod.POST)
    public void payments(@RequestBody String paymentCompleteInfo) {
        log.info("Payment complete info from payment service: " + paymentCompleteInfo);
        orderInfoService.processDeliveryInfo(paymentCompleteInfo);
    }
}
