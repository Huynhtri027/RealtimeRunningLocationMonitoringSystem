package assignment1.service;

import assignment1.domain.ResultInfo;
import assignment1.domain.RunningInfo;

import java.util.List;

public interface RunningInfoService {

    List<RunningInfo> saveRunningInfos(List<RunningInfo> runningInfos);

    void deleteByRunningId(String runningId);

    List<ResultInfo> findAllRunningInfos(int page, int size);
}
