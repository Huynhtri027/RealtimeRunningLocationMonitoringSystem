package demo.rest;

import demo.model.Activity;
import demo.model.ActivityLog;
import demo.model.FitbitInfo;
import demo.model.LifetimeActivity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@EnableOAuth2Sso
@EnableWebSecurity
@Slf4j
public class FitbitApiRestController extends WebSecurityConfigurerAdapter {
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String helloWorld() {
//        return "Hello Nike Running";
//    }


    @Autowired
    OAuth2RestTemplate fitbitOAuthRestTemplate;

    @Value("${fitbit.api.resource.activitiesUri}")
    String fitbitActivitiesUri;

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void activityLogUpdate(@RequestBody ActivityLog activityLog) {
        System.out.println(activityLog.toString());
    }

    @RequestMapping("/lifetime-activity")
    public LifetimeActivity lifetimeActivity() {
        LifetimeActivity lifetimeActivity;

        try {

            Activity a = fitbitOAuthRestTemplate.getForObject(fitbitActivitiesUri, Activity.class);
            lifetimeActivity = a.getLifetime().getTotal();

        } catch (Exception e) {

            lifetimeActivity = new LifetimeActivity();

        }

        return lifetimeActivity;
    }

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/webjars/**").permitAll().anyRequest()
                .authenticated();
        http.csrf().disable();
    }

    @RequestMapping(value = "/uploadToFitbit", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadToFitbit(@RequestBody FitbitInfo fitbitInfo) {

        System.out.println("zeyuni123++++++++++++++++++++++++++++++++++++++");
        log.info("input123: " + fitbitInfo.toString());

        log.info(fitbitInfo.getActivityId() + " " + String.valueOf(fitbitInfo.getDistance()) + " " +
                fitbitInfo.getStartDate() + " " + fitbitInfo.getStartTime());


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
//        map.add("activityId", "90019");
//        map.add("startTime", "20:00:00");
//        map.add("durationMillis", "10");
//        map.add("date", "2017-12-12");
//        map.add("distance", "12.2");

        map.add("activityId", fitbitInfo.getActivityId());
        map.add("startTime", fitbitInfo.getStartTime());
        map.add("durationMillis", "10");
        map.add("date", fitbitInfo.getStartDate());
        map.add("distance", String.valueOf(fitbitInfo.getDistance()));


        //HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        //ResponseEntity<String> response = fitbitOAuthRestTemplate.postForEntity(fitbitActivitiesUri, request, String.class);
        ResponseEntity<String> response = fitbitOAuthRestTemplate.postForEntity(fitbitActivitiesUri, map, String.class);

        //System.out.println("success123: " + response.toString());

        return response.getBody();
    }

}































































































































