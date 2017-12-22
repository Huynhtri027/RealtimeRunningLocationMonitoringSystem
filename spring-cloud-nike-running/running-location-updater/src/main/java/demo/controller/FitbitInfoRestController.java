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

//        FitbitInfo fitbitInfo1 = new FitbitInfo("90007", 1.1, 4000, "2017-12-11", "11:00", false);
//        FitbitInfo fitbitInfo2 = new FitbitInfo("90008", 2.2, 5000, "2017-12-12", "12:00", false);
//        FitbitInfo fitbitInfo3 = new FitbitInfo("90009", 3.3, 6000, "2017-12-13", "13:00", false);
//
//        List<FitbitInfo> list = new ArrayList<FitbitInfo>();
//
//        list.add(fitbitInfo1);
//        list.add(fitbitInfo2);
//        list.add(fitbitInfo3);
//
//        return list;

        return this.fitbitInfoService.findNewFitbitInfo();


    }

    @RequestMapping(value = "/saveFitbitInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody FitbitInfo fitbitInfo) {
        FitbitInfo info = this.fitbitInfoService.saveFitbitInfo(fitbitInfo);
        log.info("Save fitbitInfo from rest api to mongodb: " + info.toString());
    }

}
