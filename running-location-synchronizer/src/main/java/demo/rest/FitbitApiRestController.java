package demo.rest;

import demo.model.Activity;
import demo.model.ActivityLog;
import demo.model.FitbitInfo;
import demo.model.LifetimeActivity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.http.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

@RestController
@EnableOAuth2Sso
@EnableWebSecurity
@Slf4j
public class FitbitApiRestController extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2RestTemplate fitbitOAuthRestTemplate;

    @Autowired
    private RestTemplate restTemplate;

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

        log.info("input123: " + fitbitInfo.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

        map.add("activityId", fitbitInfo.getActivityId());
        map.add("startTime", fitbitInfo.getStartTime());
        map.add("durationMillis", "10");
        map.add("date", fitbitInfo.getStartDate());
        map.add("distance", String.valueOf(fitbitInfo.getDistance()));

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> response = fitbitOAuthRestTemplate.postForEntity(fitbitActivitiesUri, request, String.class);

        //this.restTemplate.postForLocation("http://localhost:9007/saveToFitbit", fitbitInfo);

        return response.getBody();
    }
}

//        map.add("activityId", "90019");
//        map.add("startTime", "20:00:00");
//        map.add("durationMillis", "10");
//        map.add("date", "2017-12-12");
//        map.add("distance", "12.2");






























































































































