package demo.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class Controller {

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public String save() {
        return "Hello";
        //log.info("Save fitbitInfo from rest api to mongodb: " + fitbitInfo.toString());
    }
}

