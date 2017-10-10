package assignment1.service.impl;

import assignment1.domain.ResultInfo;
import assignment1.domain.RunningInfo;
import assignment1.domain.RunningInfoRepository;
import assignment1.service.RunningInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RunningInfoServiceImpl implements RunningInfoService {
    @Autowired
    private RunningInfoRepository runningInfoRepository;

    @Override
    public List<RunningInfo> saveRunningInfos(List<RunningInfo> runningInfos) {
        for (RunningInfo runningInfo : runningInfos) {
            int heartRate = 60 + (int) (Math.random() * 141);
            //System.out.println(random);
            runningInfo.setHeartRate(heartRate);
            if (heartRate >= 60 && heartRate <= 75) {
                runningInfo.setHealthWarningLevel("LOW");
            } else if (heartRate > 75 && heartRate <= 120) {
                runningInfo.setHealthWarningLevel("NORMAL");
            } else if (heartRate > 120) {
                runningInfo.setHealthWarningLevel("HIGH");
            }
        }
        return runningInfoRepository.save(runningInfos);
    }

    @Override
    public void deleteByRunningId(String runningId) {
        runningInfoRepository.deleteByRunningId(runningId);
    }

    @Override
    public List<ResultInfo> findAllRunningInfos(int page, int size) {
        //Sort sort = new Sort(Sort.Direction.DESC, "heartRate");
        final PageRequest pageable = new PageRequest(page, size, Sort.Direction.DESC, "heartRate");
        Page<RunningInfo> lists = runningInfoRepository.findAll(pageable);

        List<ResultInfo> results = new ArrayList<ResultInfo>();

        for (RunningInfo list : lists) {
            ResultInfo result = new ResultInfo(list.getRunningId(), list.getTotalRunningTime(),
                    list.getHeartRate(), list.getId(),
                    list.getUserInfo().getUsername(),
                    list.getUserInfo().getAddress(), list.getHealthWarningLevel());

            //System.out.println(result.toString());
            results.add(result);
        }

        return results;
    }


}
