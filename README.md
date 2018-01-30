# README #
# Project Name: Realtime Running Location Monitoring System
## Author: Zeyu Ni
----------
## Overview:
Running Tracking System is a real-time application which can simulate runners' running path and sent location and activity information to Fitbit API.

## Features:
* Allow user to input the start location and the end location in front-end webpage
* Send location and activity information to Fitbit API
* Save Fitbit API response to local MySQL database

## High level design diagram:

![Alt text](https://bytebucket.org/zeyuni/realtimerunninglocationmonitoringsystem/raw/a2946ed8501638c3b5017bdc666b732af6cfff59/demoPictures/SystemDesign.png?token=3af37fa1b11a9b062221408411f8c6b2312c075b)

### Description:  
The process start when Real-time Location Simulator Service receive Http request with start and end address information, then the information is sent to Google Map API and generate Polyline for the optimal path. After that, the polyline with other running information is sent to the simulator service to simulate the running process. The real-time running information is sent to distribution service and then to RabbitMQ to decouple back-end Microservices. Updater service as consumer for RabbitMQ, acquire the running location information from MQ and sent http request to Fitbit API with location and activity information. Fitbit api received the request from updater and sent response to it. Updater service the response to local database.  

Besides, the system incorporated Netflix Eureka as service registration and discovery, used Spring Boot Actuator to monitor application health and used Hystrix as circuit breaker.

## Technology Track:  
* Backend: Java, Spring Boot, Spring Data, Spring Cloud, Netflix OSS, SQL, JPA, Maven, Tomcat, WebSocket, RabbitMQ, REST
* Frontend: HTML/CSS, Javascript, Bootstrap
* Databases: MySQL, MongoDB, H2
* Tools: Git, Docker, Vagrant, IntelliJ IDEA

## Detailed design:
### Part1: Polyline Generation and Running Location Simulation
* REST API and Http Request design in Simulator Service 
    
        @RequestMapping(value = "/input", method = RequestMethod.POST)
        @ResponseStatus(HttpStatus.CREATED)
        public void running(@RequestBody UserInputLocation startAndEnd) 
        
        Request Design
        Http: POST
        Data Type: JSON
        Fileds: String startAddress, String endAddress
        Sample:
        {
            "startAddress": "1235 Wildwood Ave, Sunnyvale, CA 94089",
            "endAddress": "Googleplex, 1600 Amphitheatre Pkwy, Mountain View, CA 94043"
        }
    
* Processed and encoded by Google Map API  

        public List<Route> getDirections(RouteLocation startLocation, RouteLocation endLocation, List<RouteLocation> waypoints)
        public EncodePolyline(java.util.List<LatLng> points)    
        
* Generate GpsSimulatorRequest object 

        public class GpsSimulatorRequest {
            private String runningId;
            private Double speed;
            private boolean move = true;
            private boolean exportPositionsToMessaging = true;
            private Integer reportInterval = 500;
            private int secondsToError = 0;
            private RunnerStatus runnerStatus = RunnerStatus.NONE;
            private String polyline;
            private MedicalInfo medicalInfo;  
        }    

* Start simulation 

        final LocationSimulator locationSimulator = gpsSimulatorFactory.prepareGpsSimulator(gpsSimulatorRequest);
        
        final Future<?> future = taskExecutor.submit(locationSimulator);

* Design front-end webpage with Javascript/HTML/CSS
        
### Part2: Fitbit API connection and response storage
* Register application from fitbit.com

* Implement an OAuth 2.0 Authorization Code Grant Flow

* Make Http Requests from updater service to Fitbit API to access data
    
        @ServiceActivator(inputChannel = Sink.INPUT)
        public void updateLocation(String input) throws IOException {
            log.info("Location input in updater: "+input);
            CurrentPosition position = this.objectMapper.readValue(input, CurrentPosition.class);
            this.template.convertAndSend("/fitbit api end point", position);
        }
        //Request Object fileds:
        public class CurrentPosition {
            private String runningId;
            private Point location;
            private RunnerStatus runnerStatus = RunnerStatus.NONE;
            private Double speed;
            private Double heading;
            //@Nike Running
            private MedicalInfo medicalInfo;
            private SupplyLocation supplyLocation;
        }    

* Fitbit API for user profile  
        
        GET https://api.fitbit.com/1/user/[user-id]/activities/date/[date].json
        POST https://api.fitbit.com/1/user/[user-id]/profile.json
        GET https://api.fitbit.com/1/user/[user-id]/badges.json

* Fitbit API for activity
	

## Future Work:
* Implement cache layer in the system using redis
* Deploy the application to AWS
