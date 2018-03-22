package demo.service;

import demo.model.DirectionLocation;
import demo.model.SimulatorInitLocations;

public interface DirectionService {

    SimulatorInitLocations generateSimulatorInitLocations(DirectionLocation locations) throws Exception;
}
