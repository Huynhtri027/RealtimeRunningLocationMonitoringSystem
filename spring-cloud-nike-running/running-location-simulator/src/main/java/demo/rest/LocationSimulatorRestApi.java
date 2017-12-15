package demo.rest;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsRoute;
import demo.model.*;
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

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Slf4j
@RestController
@RequestMapping("/api")
public class LocationSimulatorRestApi {
    String pl0 = "}_ulFvq|tM@wLtOCnG@RHhArAzDvEZZTN|DjAz@Tv@Xp@f@j@j@b@j@vAhBhB~Bh@x@Zp@b@jAdCfIjDlLrClJVt@z@hBjBhEv@~A^l@|@hAtAlA|CdCdBtA|AnAVZTh@Jd@d@nGRhDNvBbD?hG?nJ?zC@`GApI?XLdC@zEBb@?Nh@`BxGx@`Dx@pDzB~IhBhHjCrK`CxJ|AnGdC|JfAhEhCtKn@`CtAzF~@jD@ZGd@Q`@Ij@Ch@Dj@L^XZZLZFJ@VCRGHGJKPIPQV?tAn@NDrFlCfCnA\\VNLBL@`@JRFFH@PDLLLVLPPLRBNAPETKp@c@XMBCLEh@AdAANIhBCvB@dBFl@?lA?JGzBA~EAvC?xB~InBAlA?";

    MedicalInfo mi = new MedicalInfo("Fitbit", "FMW", "Fitbit", "SupplyInfo",
            "Energy Drink Required", "Check energy level before taking energy drink", "14", "17");


    @Autowired
    private PathService pathService;

    @Autowired
    private GpsSimulatorFactory gpsSimulatorFactory;

    @Autowired
    private AsyncTaskExecutor taskExecutor;

    private Map<Long, LocationSimulatorInstance> taskFutures = new HashMap<>();

    @RequestMapping(value = "/input", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestBody UserInputLocation locations) throws Exception {

        log.info("User input address: " + locations.toString());

        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyCspPtV4Z6tgRD1dgKK2sSHndTzwxcDA_0");

        DirectionsRoute[] routes = DirectionsApi.newRequest(context).origin(locations.getStartAddress()).destination(locations.getEndAddress()).await();

        String pl = routes[0].overviewPolyline.getEncodedPath();

        GpsSimulatorRequest gsr = new GpsSimulatorRequest("7c08973d-bed4-4cbd-9c28-9282a02a6032", 50.0, true,
                true, 1000, 0, RunnerStatus.SUPPLY_SOON, pl, mi);

        //assertNotNull(routes);
        //assertNotNull(routes[0]);
        log.info("Start address: " + routes[0].legs[0].startAddress);
        log.info("End address: " + routes[0].legs[0].endAddress);
        log.info("Polyline: " + routes[0].overviewPolyline.getEncodedPath());
        log.info("Summary: " + routes[0].summary);

        final List<GpsSimulatorRequest> list = new ArrayList<>();
        list.add(gsr);

        final SimulatorInitLocations fixture = new SimulatorInitLocations();
        fixture.setGpsSimulatorRequests(list);

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

//    @RequestMapping("/simulation")
//    public List<LocationSimulatorInstance> simulation() {
//
//        GpsSimulatorRequest gsr = new GpsSimulatorRequest("7c08973d-bed4-4cbd-9c28-9282a02a6032", 70.0, true,
//                true, 1000, 0, RunnerStatus.SUPPLY_SOON, pl0, mi);
//
//        final List<GpsSimulatorRequest> list = new ArrayList<>();
//        list.add(gsr);
//
//        final SimulatorInitLocations fixture = new SimulatorInitLocations();
//        fixture.setGpsSimulatorRequests(list);
//
//        final List<LocationSimulatorInstance> instances = new ArrayList<>();
//        final List<Point> lookAtPoints = new ArrayList<>();
//
//        final Set<Long> instanceIds = new HashSet<>(taskFutures.keySet());
//
//        for (GpsSimulatorRequest gpsSimulatorRequest : fixture.getGpsSimulatorRequests()) {
//
//            final LocationSimulator locationSimulator = gpsSimulatorFactory.prepareGpsSimulator(gpsSimulatorRequest);
//            lookAtPoints.add(locationSimulator.getStartPoint());
//            instanceIds.add(locationSimulator.getId());
//
//            final Future<?> future = taskExecutor.submit(locationSimulator);
//            final LocationSimulatorInstance instance = new LocationSimulatorInstance(locationSimulator
//                    .getId(), locationSimulator, future);
//            taskFutures.put(locationSimulator.getId(), instance);//cancel
//            instances.add(instance);
//        }
//
//        return instances;
//    }


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