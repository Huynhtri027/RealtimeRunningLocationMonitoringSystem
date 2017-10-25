package demo.rest;

import demo.model.OrderInfo;
import demo.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void upload(@RequestBody List<OrderInfo> orderInfo) {
        log.info("rest: " + orderInfo);
        orderInfoService.saveOrderInfo(orderInfo);
    }
}
