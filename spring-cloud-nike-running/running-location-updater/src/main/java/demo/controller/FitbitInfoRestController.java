package demo.controller;

import demo.model.FitbitInfo;
import demo.model.FitbitInfoRepository;
import demo.service.FitbitInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin
public class FitbitInfoRestController {

    @Autowired
    private FitbitInfoRepository fitbitInfoRepository;

    @Autowired
    private FitbitInfoService fitbitInfoService;


    @RequestMapping(value = "/getFitbitInfo", method = RequestMethod.GET)
    public Iterable<FitbitInfo> findNew() {
        return this.fitbitInfoService.findNewFitbitInfo();
    }

    @RequestMapping(value = "/saveFitbitInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public FitbitInfo save(@RequestBody FitbitInfo fitbitInfo) {
        FitbitInfo info = this.fitbitInfoService.saveFitbitInfo(fitbitInfo);
        log.info("Save fitbitInfo from rest api to mongodb: " + info.toString());
        return info;
    }
}
