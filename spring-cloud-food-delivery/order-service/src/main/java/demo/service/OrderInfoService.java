package demo.service;

import demo.model.OrderInfo;
import demo.model.PaymentInfo;

import java.util.List;

public interface OrderInfoService {
    OrderInfo saveOrderInfo(OrderInfo orderInfo);
    void processPaymentInfo(PaymentInfo paymentInfo);
    void processDeliveryInfo(String deliveryInfo);
}
