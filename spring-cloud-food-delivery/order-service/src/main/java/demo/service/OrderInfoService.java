package demo.service;

import demo.model.OrderInfo;

import java.util.List;

public interface OrderInfoService {
    List<OrderInfo> saveOrderInfo(List<OrderInfo> orderInfo);
}
