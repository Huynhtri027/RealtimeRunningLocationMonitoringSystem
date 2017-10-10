package assignment1.rest;

import assignment1.domain.ResultInfo;
import assignment1.domain.RunningInfo;
import assignment1.service.RunningInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RunningRestController {

    public enum HealthWarningLevel {
        LOW, NORMAL, HIGH;
    }

    @Autowired
    private RunningInfoService runningInfoService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String helloWorld() {
        return "Hello Running dff";
    }

    @RequestMapping(value = "/runningInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestBody List<RunningInfo> RunningInfos) {
        runningInfoService.saveRunningInfos(RunningInfos);
    }

    @RequestMapping(value = "/runningInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResultInfo> getAllRunningInfosSortByHealthWarningLevel(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return runningInfoService.findAllRunningInfos(page, size);
    }

    @RequestMapping(value = "/runningInfo/delete/{runningId}", method = RequestMethod.DELETE)
    public void deleteByRunningId(@PathVariable String runningId) {
        this.runningInfoService.deleteByRunningId(runningId);
    }
}
