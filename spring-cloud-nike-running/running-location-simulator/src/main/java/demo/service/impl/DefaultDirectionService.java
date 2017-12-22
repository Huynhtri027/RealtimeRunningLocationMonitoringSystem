package demo.service.impl;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsRoute;
import demo.model.*;
import demo.service.DirectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DefaultDirectionService implements DirectionService {

    private String googleMapApiKey = "AIzaSyCspPtV4Z6tgRD1dgKK2sSHndTzwxcDA_0";

    private MedicalInfo mi = new MedicalInfo("Fitbit", "FMW", "Fitbit", "SupplyInfo",
            "Energy Drink Required", "Check energy level before taking energy drink", "14", "17");

    public SimulatorInitLocations generateSimulatorInitLocations(DirectionLocation locations) throws Exception {

        log.info("User input address: " + locations.toString());

        GeoApiContext context = new GeoApiContext().setApiKey(googleMapApiKey);

        DirectionsRoute[] routes = DirectionsApi.newRequest(context).origin(locations.getStartAddress()).destination(locations.getEndAddress()).await();

        String pl = routes[0].overviewPolyline.getEncodedPath();

        RunnerStatus runnerStatus = new RunnerStatus(Status.SUPPLY_SOON, 1000, "2017-12-13", "10:00:00", false, false);

        GpsSimulatorRequest gsr = new GpsSimulatorRequest("90010", 50.0, true,
                true, 1000, 0, runnerStatus, pl, mi);

        log.info("Start address: " + routes[0].legs[0].startAddress);
        log.info("End address: " + routes[0].legs[0].endAddress);
        log.info("Polyline: " + routes[0].overviewPolyline.getEncodedPath());

        final List<GpsSimulatorRequest> list = new ArrayList<>();
        list.add(gsr);

        final SimulatorInitLocations fixture = new SimulatorInitLocations();
        fixture.setGpsSimulatorRequests(list);

        return fixture;
    }


}
