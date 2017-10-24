package demo.rest;

import demo.model.RestaurantInfo;
import demo.model.RestaurantInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class RestaurantInfoController {
    private RestaurantInfoRepository repository;

    @Autowired
    public RestaurantInfoController(RestaurantInfoRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String helloWorld() {
        return "Hello Food Delivery";
    }

    @RequestMapping(value = "/bulk/restaurants", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestBody List<RestaurantInfo> restaurantInfo) {
        log.info("Rest layer receive the post request: " + restaurantInfo);
        this.repository.save(restaurantInfo);
    }

    @RequestMapping(value = "/restaurants/{restaurantName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantInfo findByMovementType(@PathVariable String restaurantName) {
        log.info("Rest layer return the get request: " + this.repository.findByRestaurantName(restaurantName));
        return this.repository.findByRestaurantName(restaurantName);
    }
}
