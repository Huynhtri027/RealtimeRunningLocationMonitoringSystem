package demo.service;

import demo.model.FitbitInfo;

import java.util.List;

public interface FitbitInfoService {

    FitbitInfo saveFitbitInfo(FitbitInfo locations);

    Iterable<FitbitInfo> findNewFitbitInfo();
}
