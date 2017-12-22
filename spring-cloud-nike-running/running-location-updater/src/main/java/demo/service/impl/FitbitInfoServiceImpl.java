package demo.service.impl;

import demo.model.FitbitInfo;
import demo.model.FitbitInfoRepository;
import demo.service.FitbitInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FitbitInfoServiceImpl implements FitbitInfoService {

    @Autowired
    private FitbitInfoRepository fitbitInfoRepository;

    @Autowired
    public FitbitInfoServiceImpl(FitbitInfoRepository repository) {
        this.fitbitInfoRepository = repository;
    }

    @Override
    public FitbitInfo saveFitbitInfo(FitbitInfo runningLocations) {
        return fitbitInfoRepository.save(runningLocations);
    }

    @Override
    public Iterable<FitbitInfo> findNewFitbitInfo() {
        return fitbitInfoRepository.findAll();
    }
}
