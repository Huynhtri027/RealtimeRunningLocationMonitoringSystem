package demo;

import demo.domain.Location;
import demo.domain.LocationRepository;
import demo.domain.UnitInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RunningLocationServiceApplication.class)
@WebAppConfiguration
public class LocationRepositoryTest {

    @Autowired
    LocationRepository repository;

    @Test
    public void saveLocation() {
        String runningId = "ross-runningId-1";
        this.repository.save(new Location(new UnitInfo(runningId)));
        assertThat(this.repository.findByUnitInfoRunningId(runningId, new PageRequest(0, 10)).getContent().get(0).getRunningId()).isEqualTo(runningId);
    }
}