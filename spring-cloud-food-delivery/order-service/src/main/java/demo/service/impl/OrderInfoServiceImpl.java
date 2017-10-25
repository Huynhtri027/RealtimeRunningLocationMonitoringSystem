package demo.service.impl;

import demo.model.OrderInfo;
import demo.model.OrderInfoRepository;
import demo.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Override
    public List<OrderInfo> saveOrderInfo(List<OrderInfo> orderInfo) {
        log.info("Service: " + orderInfo);
        return orderInfoRepository.save(orderInfo);
    }

}
