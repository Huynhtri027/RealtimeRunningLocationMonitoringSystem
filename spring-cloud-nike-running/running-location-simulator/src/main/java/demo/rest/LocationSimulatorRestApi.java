package demo.rest;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsRoute;
import demo.model.*;
import demo.service.DirectionService;
import demo.service.GpsSimulatorFactory;
import demo.service.PathService;
import demo.task.LocationSimulator;
import demo.task.LocationSimulatorInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.Future;

@Slf4j
@RestController
@RequestMapping("/api")
public class LocationSimulatorRestApi {

    @Autowired
    private PathService pathService;

    @Autowired
    private DirectionService directionService;

    @Autowired
    private GpsSimulatorFactory gpsSimulatorFactory;

    @Autowired
    private AsyncTaskExecutor taskExecutor;

    private Map<Long, LocationSimulatorInstance> taskFutures = new HashMap<>();

    @RequestMapping(value = "/input", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestBody DirectionLocation locations) throws Exception {

        final SimulatorInitLocations fixture = directionService.generateSimulatorInitLocations(locations);

        final List<LocationSimulatorInstance> instances = new ArrayList<>();
        final List<Point> lookAtPoints = new ArrayList<>();

        final Set<Long> instanceIds = new HashSet<>(taskFutures.keySet());

        for (GpsSimulatorRequest gpsSimulatorRequest : fixture.getGpsSimulatorRequests()) {

            final LocationSimulator locationSimulator = gpsSimulatorFactory.prepareGpsSimulator(gpsSimulatorRequest);
            lookAtPoints.add(locationSimulator.getStartPoint());
            instanceIds.add(locationSimulator.getId());

            final Future<?> future = taskExecutor.submit(locationSimulator);
            final LocationSimulatorInstance instance = new LocationSimulatorInstance(locationSimulator
                    .getId(), locationSimulator, future);
            taskFutures.put(locationSimulator.getId(), instance);//cancel
            instances.add(instance);
        }
    }

    //1. loadSimulatorFixture
    //2. Transform demo.domain model simulator request to a class that can be executed by taskExecutor
    //3. taskExecutor.submit(simulator);
    //4. simulation starts
    @RequestMapping("/simulation")
    public List<LocationSimulatorInstance> simulation() {
        final SimulatorInitLocations fixture = this.pathService.loadSimulatorFixture();

        final List<LocationSimulatorInstance> instances = new ArrayList<>();
        final List<Point> lookAtPoints = new ArrayList<>();

        final Set<Long> instanceIds = new HashSet<>(taskFutures.keySet());

        for (GpsSimulatorRequest gpsSimulatorRequest : fixture.getGpsSimulatorRequests()) {

            final LocationSimulator locationSimulator = gpsSimulatorFactory.prepareGpsSimulator(gpsSimulatorRequest);
            lookAtPoints.add(locationSimulator.getStartPoint());
            instanceIds.add(locationSimulator.getId());

            final Future<?> future = taskExecutor.submit(locationSimulator);
            final LocationSimulatorInstance instance = new LocationSimulatorInstance(locationSimulator
                    .getId(), locationSimulator, future);
            taskFutures.put(locationSimulator.getId(), instance);//cancel
            instances.add(instance);
        }

        return instances;
    }

    @RequestMapping("/cancel")
    public int cancel() {
        int numberOfCancelledTasks = 0;
        for (Map.Entry<Long, LocationSimulatorInstance> entry : taskFutures.entrySet()) {
            LocationSimulatorInstance instance = entry.getValue();
            instance.getLocationSimulator().cancel();
            boolean wasCancelled = instance.getLocationSimulatorTask().cancel(true);
            if (wasCancelled) {
                numberOfCancelledTasks++;
            }
        }
        taskFutures.clear();
        return numberOfCancelledTasks;
    }
}